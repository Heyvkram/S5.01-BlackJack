package blackjack.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class GameAlreadyFinishedException extends RuntimeException {

    public GameAlreadyFinishedException(String message) {
        super(message);
    }
}
