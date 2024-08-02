package com.example.drawscribeio.service.impl;

import com.example.drawscribeio.dto.*;
import com.example.drawscribeio.dto.Mapper.*;
import com.example.drawscribeio.entity.*;
import com.example.drawscribeio.entity.LeaderBoard.LeaderBoardEntry;
import com.example.drawscribeio.entity.LeaderBoard.Leaderboard;
import com.example.drawscribeio.repository.*;
import com.example.drawscribeio.service.GameSessionService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class GameSessionServiceImpl implements GameSessionService {

    @Autowired
    private GameSessionRepository gameSessionRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private RoundRepository roundRepository;

    @Autowired
    private LeaderboardRepository leaderboardRepository;

    @Autowired
    private LeaderBoardEntryRepository leaderBoardEntryRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private WordRepository wordRepository;

    @Autowired
    private WordServiceImpl wordServiceImpl;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    public GameSessionServiceImpl(GameSessionRepository gameSessionRepository) {
        this.gameSessionRepository=gameSessionRepository;
    }

    @Override
    public List<GameSession> getAllGameSessions() {
        return gameSessionRepository.findAll();
    }

    @Override
    public GameSession getGameSessionById(Long gameSessionId) {
        GameSession gameSession = gameSessionRepository.findById(gameSessionId).orElseThrow(() -> new RuntimeException("GameSession with given Id not found"));
        return gameSession;
    }


    @Override
    public GameSession createGameSession(GameSession gameSession, Long userId) {

        // Generate a unique game session code
        String gameSessionCode = UUID.randomUUID().toString();
        gameSession.setSession_code(gameSessionCode);

        User user = userRepository.findById(userId).orElseThrow(()->new EntityNotFoundException("User not found with given id " + userId));
        gameSession.getUsers().add(user);

        // Ensure the leaderboard is initialized
        Leaderboard leaderBoard = gameSession.getLeaderboards();
        if (leaderBoard == null) {
            leaderBoard = new Leaderboard();
            leaderBoard.setGameSession(gameSession);
            gameSession.setLeaderboards(leaderBoard);
        } else {
            leaderBoard.setGameSession(gameSession);
        }

        // Persist the leaderboard (if new)
        if (leaderBoard.getLeaderboardId() == null) {
            leaderboardRepository.save(leaderBoard);
        }

        // Set the leaderboard in all entries
        for (LeaderBoardEntry entry : leaderBoard.getEntries()) {
            entry.setLeaderboard(leaderBoard);
            leaderBoardEntryRepository.save(entry);
        }

        // Initialize scores and set the relationship
        Set<Score> scores = new HashSet<>();
        for (User sessionUser : gameSession.getUsers()) {
            Score score = new Score();
            score.setGameSessions(gameSession);
            score.setUser(sessionUser);
            score.setPoints(0); // Initial score
            scores.add(score);
            scoreRepository.save(score);
        }
        gameSession.setScores(scores);

        // Initialize rounds and set the relationship
        Set<Round> rounds = new HashSet<>();
        Round round = new Round();
        round.setGameSessions(gameSession);
        round.setCurrentDrawer(user); // Set the initial drawer
        round.setRoundNumber(1); // Initial round number
        rounds.add(round);
        roundRepository.save(round);
        gameSession.setRounds((List<Round>) rounds);

        // Persist the game session and its leaderboards
        GameSession savedgameSession = gameSessionRepository.save(gameSession);

        return savedgameSession;
    }

    // Method to update an existing game session by adding a new user

    public GameSessionDto updateGameSession(Long gameSessionId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        GameSession gameSession = gameSessionRepository.findById(gameSessionId)
                .orElseThrow(() -> new EntityNotFoundException("GameSession not found with id: " + gameSessionId));

        gameSession.getUsers().add(user);
        GameSession updatedGameSession = gameSessionRepository.save(gameSession);

        GameSessionDto gameSessionDTO = new GameSessionDto();
        gameSessionDTO.setGameSessionId(updatedGameSession.getGame_sessionId());
        gameSessionDTO.setSessionCode(updatedGameSession.getSession_code());

        List<UserDto> userDTOs = updatedGameSession.getUsers().stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
        gameSessionDTO.setUsers(userDTOs);

        List<ScoreDto> scoreDTOs = updatedGameSession.getScores().stream()
                .map(ScoreMapper::toScoreDTO)
                .collect(Collectors.toList());
        gameSessionDTO.setScores(scoreDTOs);

        List<RoundDto> roundDTOs = updatedGameSession.getRounds().stream()
                .map(RoundMapper::toRoundDTO)
                .collect(Collectors.toList());
        gameSessionDTO.setRounds(roundDTOs);

        return gameSessionDTO;
    }

    public GameSessionDto joinOngoingGame(Long gameSessionId, Long userId) {
        // Fetch the user and game session from the repository
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        GameSession gameSession = gameSessionRepository.findById(gameSessionId)
                .orElseThrow(() -> new EntityNotFoundException("GameSession not found with id: " + gameSessionId));

        // Add the user to the game session
        gameSession.getUsers().add(user);
        GameSession updatedGameSession = gameSessionRepository.save(gameSession);

        // Convert GameSession to GameSessionDTO
        GameSessionDto gameSessionDTO = new GameSessionDto();
        gameSessionDTO.setGameSessionId(updatedGameSession.getGame_sessionId());
        gameSessionDTO.setSessionCode(updatedGameSession.getSession_code());

        // Convert List<User> to List<UserDTO>
        List<UserDto> userDTOs = updatedGameSession.getUsers().stream()
                .map(UserMapper::toUserDTO)
                .collect(Collectors.toList());
        gameSessionDTO.setUsers(userDTOs);

        // Convert List<Score> to List<ScoreDTO>
        List<ScoreDto> scoreDTOs = updatedGameSession.getScores().stream()
                .map(ScoreMapper::toScoreDTO)
                .collect(Collectors.toList());
        gameSessionDTO.setScores(scoreDTOs);

        // Convert List<Round> to List<RoundDTO>
        List<RoundDto> roundDTOs = updatedGameSession.getRounds().stream()
                .map(RoundMapper::toRoundDTO)
                .collect(Collectors.toList());
        gameSessionDTO.setRounds(roundDTOs);

        return gameSessionDTO;
    }


    public GameSessionDto startRound(Long gameSessionId) {
        GameSession gameSession = gameSessionRepository.findById(gameSessionId)
                .orElseThrow(() -> new EntityNotFoundException("GameSession not found with id: " + gameSessionId));

        // Start the first round
        startTimer(gameSession);

        return GameSessionMapper.toGameSessionDTO(gameSession);
    }

    private void startTimer(GameSession gameSession) {
        scheduler.schedule(() -> endRound(gameSession), 60, TimeUnit.SECONDS);
    }

    private void endRound(GameSession gameSession) {
        // Logic to end the current round and rotate to the next user
        Round currentRound = roundRepository.findByGameSessionAndCurrentRound(gameSession.getGame_sessionId())
                .orElseThrow(() -> new EntityNotFoundException("Current round not found for gameSession with id: " + gameSession.getGame_sessionId()));

        // Update round to end current round
        currentRound.setIsCurrentRound(false);
        roundRepository.save(currentRound);

        // Find the next user in line
        List<User> users = gameSession.getUsers();
        int currentIndex = users.indexOf(currentRound.getCurrentDrawer());
        int nextIndex = (currentIndex + 1) % users.size();
        User nextUser = users.get(nextIndex);

        // Create a new round
        Round newRound = new Round();
        newRound.setGameSessions(gameSession);
        newRound.setCurrentDrawer(nextUser);
        newRound.setRoundNumber(currentRound.getRoundNumber() + 1);
        newRound.setIsCurrentRound(true);
        roundRepository.save(newRound);

        // Start the next round
        startTimer(gameSession);
    }


    public List<WordDto> getThreeRandomWords() {
        List<Word> words = wordRepository.findAll();
        Collections.shuffle(words);
        return words.stream().limit(3).map(WordMapper::toWordDTO).collect(Collectors.toList());
    }


    public GameSessionDto startNewRound(Long gameSessionId, Long wordId) {
        GameSession gameSession = gameSessionRepository.findById(gameSessionId)
                .orElseThrow(() -> new EntityNotFoundException("GameSession not found with id: " + gameSessionId));

        User currentDrawer = gameSession.getCurrentPlayer();

        Word word = wordRepository.findById(wordId)
                .orElseThrow(() -> new EntityNotFoundException("Word not found with id: " + wordId));

        Round round = new Round();
        round.setGameSessions(gameSession);
        round.setCurrentDrawer(currentDrawer);
        round.setDrawingWord(word);
        round.setRoundNumber(gameSession.getCurrentRound());

        roundRepository.save(round);
        gameSession.getRounds().add(round);

        gameSessionRepository.save(gameSession);

        // Schedule the next player's turn
        scheduleNextTurn(gameSessionId, 60);

        return GameSessionMapper.toGameSessionDTO(gameSession);
    }

    private void scheduleNextTurn(Long gameSessionId, int delayInSeconds) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.schedule(() -> {
            proceedToNextPlayer(gameSessionId);
        }, delayInSeconds, TimeUnit.SECONDS);
    }

    public void proceedToNextPlayer(Long gameSessionId) {
        GameSession gameSession = gameSessionRepository.findById(gameSessionId)
                .orElseThrow(() -> new EntityNotFoundException("GameSession not found with id: " + gameSessionId));

        gameSession.nextPlayer();
        gameSessionRepository.save(gameSession);

        if (gameSession.getCurrentCycle() <= 3) {
            // Get three random words and notify the current player to choose one
            List<WordDto> threeWords = getThreeRandomWords();
            User currentPlayer = gameSession.getCurrentPlayer();

            // Notify the current player to choose one of the three words (implementation depends on your notification mechanism)
        } else {
            // End the game
//            gameSession.endGame();
        }
    }

    // Example method to send game state to the user (implement according to your needs)
    private void sendGameStateToUser(User user, GameSession gameSession, Round currentRound) {
        // Logic to send current game state to the user
        // For example, you can use WebSocket, email, or another notification system
    }


    @Override
    public void deleteGameSession(Long gameSessionId) {
        gameSessionRepository.deleteById(gameSessionId);
    }




    public void calculateScores(Long sessionId) {
        GameSession gameSession = gameSessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFoundException("GameSession not found with id: " + sessionId));

        // Logic to calculate scores
        // Example: Iterate over rounds and calculate scores for each user
        for (Round round : gameSession.getRounds()) {
            for (User user : gameSession.getUsers()) {
                // Custom scoring logic based on game rules
                int scoreValue = calculateScoreForUser(round, user);
                Score score = new Score();
                score.setGameSessions(gameSession);
                score.setUser(user);
                score.setPoints(scoreValue);
                scoreRepository.save(score);
            }
        }
    }

    private int calculateScoreForUser(Round round, User user) {
        // Implement game-specific scoring logic here
        return 0; // Placeholder score calculation
    }

    public GameSessionDto getGameSessionDto(Long gameSessionId) {
       Optional<GameSession> gs= gameSessionRepository.findById(gameSessionId);
       GameSessionDto gsDto = new GameSessionDto();

       gsDto.setGameSessionId(gs.get().getGame_sessionId());
       gsDto.setUsers(gs.get().getUsers().stream().map(UserMapper::toUserDTO).collect(Collectors.toList()));
       gsDto.setScores(gs.get().getScores().stream().map(ScoreMapper::toScoreDTO).collect(Collectors.toList()));
       gsDto.setRounds(gs.get().getRounds().stream().map(RoundMapper::toRoundDTO).collect(Collectors.toList()));
        return gsDto;

//        private String sessionCode;
//        private List<UserDto> users;
//        private List<ScoreDto> scores;
//        private List<RoundDto> rounds;
//        private LeaderboardDto leaderboard;
//        return null;
    }
}
