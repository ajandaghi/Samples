package Utils;

import Repository.Session;
import exception.ProcessException;

@FunctionalInterface
public interface SessionExecutor {
    void runMethod(Session session) throws ProcessException;

}
