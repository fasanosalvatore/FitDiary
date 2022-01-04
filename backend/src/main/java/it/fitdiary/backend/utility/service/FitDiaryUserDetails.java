package it.fitdiary.backend.utility.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class FitDiaryUserDetails extends User implements UserDetails {

    private Long id;
    /**
     * String of the name.
     */
    private String name;
    /**
     * String of the phoneNumber.
     */
    private String phoneNumber;
    /**
     * String of 1 char representing the gender.
     */
    private String gender;
    /**
     * String of the surname.
     */
    private String surname;
    /**
     * Id of the trainer.
     */
    private long trainer;

    /**
     * @param username    User username.
     * @param password    User password.
     * @param authorities Authorities of the user.
     */
    public FitDiaryUserDetails(final String username,
                               final String password,
                               final Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    /**
     * @return the id of the user.
     */
    public Long getId() {
        return id;
    }

    /**
     * @return the Name of the user.
     */
    public String getName() {
        return name;
    }

    /**
     * @return the email of the user.
     */
    public String getEmail() {
        return super.getUsername();
    }

    /**
     * @return the phone number of the user.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * @return the gender of the user.
     */
    public String getGender() {
        return gender;
    }

    /**
     * @return the trained id of the user.
     */
    public long getTrainer() {
        return trainer;
    }

    /**
     * @return the surname of the user.
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param nome of the user.
     */
    public void setName(final String nome) {
        this.name = nome;
    }

    /**
     * @param surn of the user.
     */
    public void setSurname(final String surn) {
        this.surname = surn;
    }

    /**
     * @param phoneNumb of the user.
     */
    public void setPhoneNumber(final String phoneNumb) {
        this.phoneNumber = phoneNumb;
    }

    /**
     * @param gend of the user.
     */
    public void setGender(final String gend) {
        this.gender = gend;
    }

    /**
     * @param trainerId of the user.
     */
    public void setTrainerId(final long trainerId) {
        this.trainer = trainerId;
    }

    /**
     * @param id of the user
     */
    public void setId(Long id) {
        this.id = id;
    }
}
