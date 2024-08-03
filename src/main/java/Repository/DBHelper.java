package Repository;

public class DBHelper {
    private static Session session;

    public static Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
