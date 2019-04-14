package android.example.autoattendie;

public class Person {
    private String Id;
    private String Password;

    public Person(String Id, String Password) {
        this.Id = Id;
        this.Password = Password;
    }

    public String getId() {
        return Id;
    }

    public void setId(String name) {
        this.Id = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Id = Password;
    }
}