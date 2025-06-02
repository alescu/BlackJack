package cat.itacademy.s05.t01.n01.bussines;

import cat.itacademy.s05.t01.n01.business.BlackJackBusiness;
import cat.itacademy.s05.t01.n01.model.Card;
import cat.itacademy.s05.t01.n01.model.Game;
import cat.itacademy.s05.t01.n01.model.Player;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GameResolverTest {

    @InjectMocks
    private BlackJackBusiness blackJackBusiness;

    @Mock
    private Player mockPlayer;
    @Mock
    private Game mockGame;

    // Dades comunes per als tests
    private static final double INITIAL_BET = 10.0;
    private static final double INITIAL_ACCOUNT = 100.0;

    @BeforeEach
    void setUp() {
        reset(mockPlayer, mockGame);
        when(mockGame.getTotalBet()).thenReturn(INITIAL_BET);
        when(mockPlayer.getAccount()).thenReturn(INITIAL_ACCOUNT);
        when(mockPlayer.getProfitBalance()).thenReturn(0.0);
        when(mockPlayer.getGamesLost()).thenReturn(0);
        when(mockPlayer.getGamesDraw()).thenReturn(0);
        when(mockPlayer.getGamesWon()).thenReturn(0);
    }

    @Test
    public void resolveGame_bancaWins() {
        when(mockGame.getResultCode()).thenReturn(1);
        blackJackBusiness.resolveGame(mockPlayer, mockGame);
        verify(mockGame).setResultMessage("La banca guanya");
        verify(mockPlayer).addLostGame();
        verify(mockPlayer, never()).addWinGame();
        verify(mockPlayer, never()).addDrawGame();
        verify(mockPlayer, never()).addProfit(anyDouble());
        verify(mockPlayer, never()).addAccount(anyDouble());
    }

    @Test
    public void resolveGame_draw() {
        when(mockGame.getResultCode()).thenReturn(2);
        when(mockGame.getTotalBet()).thenReturn(INITIAL_BET);
        blackJackBusiness.resolveGame(mockPlayer, mockGame);
        verify(mockGame).setResultMessage("Empat");
        verify(mockPlayer).addDrawGame();
        verify(mockPlayer).addProfit(INITIAL_BET);
        verify(mockPlayer).addAccount(INITIAL_BET);
        verify(mockPlayer, never()).addWinGame();
        verify(mockPlayer, never()).addLostGame();
    }


    // [ToDo]
}
