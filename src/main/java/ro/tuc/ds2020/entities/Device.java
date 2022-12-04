package ro.tuc.ds2020.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Device implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "device_sequence")
    @SequenceGenerator(name = "device_sequence", sequenceName = "device_sequence", allocationSize = 1)
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "address")
    private String address;

    @Column(name = "max_consumption", nullable = false)
    private int maxConsptPerHour;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private Accounts accounts;

    public Device(Long id, String description, String address, int maxConsptPerHour, Accounts accounts) {
        this.id = id;
        this.description = description;
        this.address = address;
        this.maxConsptPerHour = maxConsptPerHour;
        this.accounts = accounts;
    }

    public Device(String description, String address, int maxConsptPerHour, Accounts accounts) {
        this.description = description;
        this.address = address;
        this.maxConsptPerHour = maxConsptPerHour;
        this.accounts = accounts;
    }

    public Device(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMaxConsptPerHour() {
        return maxConsptPerHour;
    }

    public void setMaxConsptPerHour(int maxConsptPerHour) {
        this.maxConsptPerHour = maxConsptPerHour;
    }

    public Accounts getAccounts() {
        return accounts;
    }

    public void setAccounts(Accounts accounts) {
        this.accounts = accounts;
    }
}
