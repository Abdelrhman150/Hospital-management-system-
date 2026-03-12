package Package1;

public abstract class User {
    protected int id;
    protected String name;
    protected String phone;
    protected String email;

    public User( int ID ,String name, String phone, String email) {
        this.id = ID;
        this.name = name;
        this.phone = phone;
        this.email = email;
    }

    public User() {
        //TODO Auto-generated constructor stub
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public abstract String getRole();

    public void displayInfo() {
        System.out.println("User ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Role: " + getRole());
    }
}