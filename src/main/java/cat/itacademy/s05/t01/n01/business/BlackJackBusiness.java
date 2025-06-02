package cat.itacademy.s05.t01.n01.business;

import cat.itacademy.s05.t01.n01.model.Card;
import cat.itacademy.s05.t01.n01.model.Game;
import cat.itacademy.s05.t01.n01.model.GameMovement;
import cat.itacademy.s05.t01.n01.model.Player;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BlackJackBusiness {

    public int getTotalPoints(String cardName, int totalPoints) {
        return getCardValue(cardName, totalPoints) + totalPoints;
    }

    public int getHashCode(List<Card> cardList) {
        return Arrays.hashCode(cardList.toArray());
    }

    public void generateDealerMovements(Game game) {

        boolean dealerGameConditions = false;
        int dealerPoints = 0;
        do{
            GameMovement dealerMove = GameMovement.builder()
                    .id(game.getDealerMoves().size())
                    .moveType(GameOptions.HIT)
                    .build();

            Card nextCard = game.getDeckCards().removeFirst();
            int cardValue = getCardValue(nextCard.getName(), game.getDealerPoints());
            dealerMove.setCardName(nextCard.getName());
            dealerMove.setCardPoints(cardValue);
            game.getDealerCards().add(nextCard);

            dealerPoints += getCardValue(nextCard.getName(), dealerPoints);

            if(((game.getPlayerPoints() < dealerPoints) && (dealerPoints > 17) || (dealerPoints > 21))){
                dealerGameConditions = true;
                dealerMove.setMoveType(GameOptions.STAND);
            }

            game.setDealerPoints(dealerPoints);
            game.getDealerMoves().add(dealerMove);

        }while(!dealerGameConditions);

        if(dealerPoints>21 || (game.getPlayerPoints() > dealerPoints)){
            game.setResultCode(3);
        }else if(game.getPlayerPoints() < dealerPoints){
            game.setResultCode(1);
        }else {
            game.setResultCode(2);
        }

    }

    public int getCardValue(String cardName, int totalPoints) {
        String normalizedCardName = cardName.toLowerCase();
        switch (normalizedCardName) {
            case "2 of spades":
            case "2 of hearts":
            case "2 of diamonds":
            case "2 of clubs":
                return 2;
            case "3 of spades":
            case "3 of hearts":
            case "3 of diamonds":
            case "3 of clubs":
                return 3;
            case "4 of spades":
            case "4 of hearts":
            case "4 of diamonds":
            case "4 of clubs":
                return 4;
            case "5 of spades":
            case "5 of hearts":
            case "5 of diamonds":
            case "5 of clubs":
                return 5;
            case "6 of spades":
            case "6 of hearts":
            case "6 of diamonds":
            case "6 of clubs":
                return 6;
            case "7 of spades":
            case "7 of hearts":
            case "7 of diamonds":
            case "7 of clubs":
                return 7;
            case "8 of spades":
            case "8 of hearts":
            case "8 of diamonds":
            case "8 of clubs":
                return 8;
            case "9 of spades":
            case "9 of hearts":
            case "9 of diamonds":
            case "9 of clubs":
                return 9;
            case "10 of spades":
            case "10 of hearts":
            case "10 of diamonds":
            case "10 of clubs":
            case "jack of spades":
            case "jack of hearts":
            case "jack of diamonds":
            case "jack of clubs":
            case "queen of spades":
            case "queen of hearts":
            case "queen of diamonds":
            case "queen of clubs":
            case "king of spades":
            case "king of hearts":
            case "king of diamonds":
            case "king of clubs":
                return 10;
            case "ace of spades":
            case "ace of hearts":
            case "ace of diamonds":
            case "ace of clubs":
                return (totalPoints+11>21)?1:11;
            default:
                System.err.println("Carta no reconeguda: " + cardName);
                return -1;
        }
    }

    public void resolveGame(Player player, Game game){
        if (game.getResultCode() == 1) {
            game.setResultMessage("La banca guanya");
            player.addLostGame();
        } else{
            if ((game.getResultCode() == 2)) {
                game.setResultMessage("Empat");
                player.addProfit(game.getTotalBet());
                player.addDrawGame();
                player.addProfit(game.getTotalBet());
                player.addAccount(game.getTotalBet());
            } else if ((game.getResultCode() == 3)) {
                if(game.getPlayerPoints()==21 && game.getCardsReceived().size()==2){
                    game.setResultMessage("La banca perd. Tens BlackJack");
                    player.addWinGame();
                    player.addProfit(game.getTotalBet()*3);
                    player.addAccount(game.getTotalBet()*3);
                }else{
                    game.setResultMessage("La banca perd");
                    player.addWinGame();
                    player.addProfit(game.getTotalBet()*2);
                    player.addAccount(game.getTotalBet()*2);
                }
            }

        }
    }

}
