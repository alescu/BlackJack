package cat.itacademy.s05.t01.n01.services;

import cat.itacademy.s05.t01.n01.model.Card;
import cat.itacademy.s05.t01.n01.repository.CardRepository;
import lombok.Data;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Data
@Service
public class CardService {


    private final Random random = new Random();
    private CardRepository cardRepository;

    CardService(CardRepository cardRepository){
        this.cardRepository = cardRepository;
    }

    private Flux<Card> getCardsListMono() {
        return getCardRepository().findAll();
    }

    public Mono<List<Card>> getShuffledCardsListMono() {
        return cardRepository.findAll()
                .collectList()
                .map(cards -> {
                    List<Card> mutableCards = new ArrayList<>(cards);
                    Collections.shuffle(mutableCards, random);
                    return mutableCards;
                });
    }

}
