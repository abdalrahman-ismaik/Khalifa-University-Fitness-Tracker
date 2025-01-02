import java.util.*;

public class Main {
    private static List<User> users = new ArrayList<User>();
    private static int idx;
    private static boolean checked;

    public static void addUser(User user){users.add(user);}
    public static List<User> getUsers() {return users;}

    public static void setIdx(int id){idx = id;}
    public static int getIdx(){return idx;}
    
    public static void setChecked(boolean ch){checked = ch;}
    public static boolean getChecked(){return checked;}

    public static void main(String[] args) {
		users = Database.loadUsers(); // No exception is propagated here
		SignIn signInFrame = new SignIn();
		signInFrame.pack();
		signInFrame.setLocationRelativeTo(null);
		signInFrame.setVisible(true);
        Database.saveUsers(users);
	}	
}
