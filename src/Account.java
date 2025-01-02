public class Account {
    private String name;
    protected String id;
    private String password;

    public Account(){}

    public Account(String name, String password) {
        setName(name);
        setPassword(password);
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        this.name = name.trim();
    }

    public void setPassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Password must contain at least one digit.");
        }
        if (!password.matches(".*[a-zA-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one letter.");
        }
        this.password = password;
    }

    @Override
    public String toString() {
        return "Account [Name=" + name + ", ID=" + id + "]";
    }
}
