package cat.itacademy.s05.t01.n01.services;

import cat.itacademy.s05.t01.n01.model.Card;
import cat.itacademy.s05.t01.n01.repository.CardRepository;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.stereotype.Service;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


@RunWith(SpringRunner.class)
@DataR2dbcTest
public class CardServiceTests {

    @Autowired
    private CardService cardService;

    @Test
    public void getCardsListMono() {
        Flux<Card> cardsFlux = cardService.getCardsListMono();
        StepVerifier.create(cardsFlux)
                .expectNextCount(52)
                .verifyComplete();
    }

    /*
    @Test
    public void getShuffledCardsListMono() {
         cardRepository.findAll()
                .collectList()
                .map(cards -> {
                    List<Card> mutableCards = new ArrayList<>(cards);
                    Collections.shuffle(mutableCards, new Random());
                    return mutableCards;
                });
    }
    */

}
