package cat.itacademy.s05.t01.n01.bussines;

import cat.itacademy.s05.t01.n01.business.BlackJackBusiness;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(MockitoJUnitRunner.class)
public class BlackJackBusinessTest {

    @Autowired
    private BlackJackBusiness blackJackBussines;

    @Test
    public void getCardValue_ace_1() {
        int card_1_value = blackJackBussines.getCardValue( "ace of spades", 11);
        Assertions.assertEquals(card_1_value, 1);
    }

    @Test
    public void getCardValue_ace_11() {
        int card_1_value = blackJackBussines.getCardValue( "ace of spades", 1);
        Assertions.assertEquals(card_1_value, 11);
    }



}
