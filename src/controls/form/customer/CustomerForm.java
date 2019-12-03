/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controls.form.customer;

import application.SchedulingApplication;
import dao.AddressDao;
import dao.CityDao;
import dao.CountryDao;
import java.time.Instant;
import java.util.function.Consumer;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.Skin;
import models.Address;
import models.Country;
import models.City;
import models.Customer;

import static java.util.Objects.requireNonNull;

/**
 * The {@link Control} used for creating and editing {@link Customer}s.
 *
 * @author maxbrown
 */
public class CustomerForm extends Control {
    
    private Customer customer;
    
    private final AddressDao addressDao = new AddressDao();
    
    private final CountryDao countryDao = new CountryDao();
    
    private final CityDao cityDao = new CityDao();
    
    public CustomerForm() {
        String stylesheet = getClass().getResource("customer-form.css").toExternalForm();
        getStylesheets().add(stylesheet);

        // Populate the list of countries
        countryDao.findAll().forEach(maybeCountry
                -> maybeCountry.ifPresent(c -> getCountries().add(c))
        );

        // Populate the list of cities
        cityDao.findAll().forEach(
                maybeCity -> maybeCity.ifPresent(c -> getCities().add(c))
        );
        
        phoneProperty().addListener((obs, o, n) -> System.out.println(n));
    }
    
    private final ObservableList<Country> countries = FXCollections.observableArrayList();

    /**
     * Returns the value of {@link CustomerForm#countries}.
     *
     * @return The list of countries.
     */
    public final ObservableList<Country> getCountries() {
        return countries;
    }
    
    private final ObservableList<City> cities = FXCollections.observableArrayList();

    /**
     * Returns the value of {@link CustomerForm#cities}.
     *
     * @return The list of cities.
     */
    public final ObservableList<City> getCities() {
        return cities;
    }
    
    private final StringProperty name = new SimpleStringProperty(this, "name");

    /**
     * A property containing the value of the {@link Customer}'s name.
     *
     * @return The name property.
     */
    public final StringProperty nameProperty() {
        return name;
    }

    /**
     * Returns the value of the {@link CustomerForm#nameProperty()}.
     *
     * @return The name.
     */
    public final String getName() {
        return nameProperty().get();
    }

    /**
     * Sets the value of the {@link CustomerForm#nameProperty()}.
     *
     * @param name The name to set.
     */
    public final void setName(String name) {
        requireNonNull(name);
        nameProperty().set(name);
    }
    
    private final BooleanProperty active = new SimpleBooleanProperty(this, "active", true);

    /**
     * A property containing the value of whether or not the {@link Customer} is
     * active.
     *
     * @return The active property.
     */
    public final BooleanProperty activeProperty() {
        return active;
    }

    /**
     * Returns the value of the {@link CustomerForm#activeProperty()}.
     *
     * @return True if the customer is active, otherwise false.
     */
    public final boolean isActive() {
        return activeProperty().get();
    }

    /**
     * Sets the value of the {@link CustomerForm#activeProperty()}.
     *
     * @param active True if the customer is active, otherwise false.
     */
    public final void setActive(boolean active) {
        requireNonNull(active);
        activeProperty().set(active);
    }
    
    private final StringProperty address = new SimpleStringProperty(this, "address");

    /**
     * A property containing the value of the {@link Customer}'s {@link Address}
     * address.
     *
     * @return The address property.
     */
    public final StringProperty addressProperty() {
        return address;
    }

    /**
     * Returns the value of the {@link CustomerForm#addressProperty()}.
     *
     * @return The address.
     */
    public final String getAddress() {
        return addressProperty().get();
    }

    /**
     * Sets the value of the {@link CustomerForm#addressProperty()}.
     *
     * @param address The address to set.
     */
    public final void setAddress(String address) {
        requireNonNull(address);
        addressProperty().set(address);
    }
    
    private final StringProperty address2 = new SimpleStringProperty(this, "address2");

    /**
     * A property containing the value of the {@link Customer}'s {@link Address}
     * address2.
     *
     * @return The address2 property.
     */
    public final StringProperty address2Property() {
        return address2;
    }

    /**
     * Returns the value of the {@link CustomerForm#address2Property()}.
     *
     * @return The address2.
     */
    public final String getAddress2() {
        return address2Property().get();
    }

    /**
     * Sets the value of the {@link CustomerForm#address2Property()}.
     *
     * @param address2 The address2 to set.
     */
    public final void setAddress2(String address2) {
        requireNonNull(address2);
        address2Property().set(address2);
    }
    
    private final StringProperty postalCode = new SimpleStringProperty(this, "postalCode");

    /**
     * A property containing the value of the {@link Customer}'s {@link Address}
     * postal code.
     *
     * @return The postal code property.
     */
    public final StringProperty postalCodeProperty() {
        return postalCode;
    }

    /**
     * Returns the value of the {@link CustomerForm#postalCodeProperty()}.
     *
     * @return The postal code.
     */
    public final String getPostalCode() {
        return postalCodeProperty().get();
    }

    /**
     * Sets the value of the {@link CustomerForm#postalCodeProperty()}.
     *
     * @param postalCode The postal code to set.
     */
    public final void setPostalCode(String postalCode) {
        requireNonNull(postalCode);
        postalCodeProperty().set(postalCode);
    }
    
    private final StringProperty phone = new SimpleStringProperty(this, "phone");

    /**
     * A property containing the value of the {@link Customer} phone number.
     *
     * @return The phone property.
     */
    public final StringProperty phoneProperty() {
        return phone;
    }

    /**
     * Returns the value of the {@link CustomerForm#phoneProperty()}.
     *
     * @return The phone.
     */
    public final String getPhone() {
        return phoneProperty().get();
    }

    /**
     * Sets the value of the {@link CustomerForm#phoneProperty()}.
     *
     * @param phone The phone to set.
     */
    public final void setPhone(String phone) {
        requireNonNull(phone);
        phoneProperty().set(phone);
    }
    
    private final ObjectProperty<City> city = new SimpleObjectProperty<>(this, "city", null);

    /**
     * A property containing the value of the {@link Customer}'s {@link Address}
     * {@link City}.
     *
     * @return The city property.
     */
    public final ObjectProperty<City> cityProperty() {
        return city;
    }

    /**
     * Returns the value of the {@link CustomerForm#cityProperty()}.
     *
     * @return The city.
     */
    public final City getCity() {
        return cityProperty().get();
    }

    /**
     * Sets the value of the {@link CustomerForm#cityProperty()}.
     *
     * @param city The city to set.
     */
    public final void setCity(City city) {
        requireNonNull(city);
        cityProperty().set(city);
    }
    
    private final ObjectProperty<Country> country = new SimpleObjectProperty<>(this, "country", null);

    /**
     * A property containing the value of the {@link Customer}'s
     * {@link Address} {@link Country}.
     *
     * @return The country property.
     */
    public final ObjectProperty<Country> countryProperty() {
        return country;
    }

    /**
     * Returns the value of the {@link CustomerForm#countryProperty()}.
     *
     * @return The country.
     */
    public final Country getCountry() {
        return countryProperty().get();
    }

    /**
     * Sets the value of the {@link CustomerForm#countryProperty()}.
     *
     * @param country The country to set.
     */
    public final void setCountry(Country country) {
        requireNonNull(country);
        countryProperty().set(country);
    }
    
    private final StringProperty error = new SimpleStringProperty(this, "error");

    /**
     * A property containing the value of the {@link CustomerForm} error.
     *
     * @return The error property.
     */
    public final StringProperty errorProperty() {
        return error;
    }

    /**
     * Returns the value of the {@link CustomerForm#errorProperty()}.
     *
     * @return The form error.
     */
    public final String getError() {
        return errorProperty().get();
    }

    /**
     * Sets the value of the {@link CustomerForm#errorProperty()}.
     *
     * @param error The form error to set.
     */
    public final void setError(String error) {
        requireNonNull(error);
        errorProperty().set(error);
    }
    
    private final BooleanProperty valid = new SimpleBooleanProperty(this, "valid", false);

    /**
     * A property containing the value of whether or not the
     * {@link AppointmentForm} is in a valid state.
     *
     * @return The valid property.
     */
    public final BooleanProperty validProperty() {
        return valid;
    }

    /**
     * Returns the value of the {@link AppointmentForm#validProperty()}.
     *
     * @return True if the form is in a valid state, otherwise false.
     */
    public final boolean isValid() {
        validateForm();
        return validProperty().get();
    }

    /**
     * Sets the value of the {@link AppointmentForm#validProperty()}.
     *
     * @param valid True if the form is in a valid state, otherwise false.
     */
    public final void setValid(boolean valid) {
        requireNonNull(valid);
        validProperty().set(valid);
    }
    
    private final ObjectProperty<Consumer<Customer>> onSubmit = new SimpleObjectProperty<>(this, "onSubmit");

    /**
     * A property containing the {@link Consumer} that will be called on form
     * submission.
     *
     * @return The on submit property.
     */
    public final ObjectProperty<Consumer<Customer>> onSubmitProperty() {
        return onSubmit;
    }

    /**
     * Returns the value of the {@link CustomerForm#onSubmitProperty()}.
     *
     * @return The on submit consumer.
     */
    public final Consumer<Customer> getOnSubmit() {
        return onSubmitProperty().get();
    }

    /**
     * Sets the value of the {@link CustomerForm#onSubmitProperty()}.
     *
     * @param consumer The on submit consumer to set.
     */
    public final void setOnSubmit(Consumer<Customer> consumer) {
        requireNonNull(consumer);
        onSubmitProperty().set(consumer);
    }

    /**
     * Submits the {@link CustomerForm}.
     */
    public final void submit() {
        validateForm();
        
        if (isValid()) {
            submitForm();
        }
    }

    /**
     * Sets the initial values of the {@link CustomerForm}.
     *
     * @param customer The customer values to set.
     */
    public final void setInitialValues(Customer customer) {
        this.customer = customer;
        setName(customer.getName());
        setActive(customer.isActive());
        setAddress(customer.getAddress().getAddress());
        setAddress2(customer.getAddress().getAddress2());
        setPostalCode(customer.getAddress().getPostalCode());
        setPhone(customer.getAddress().getPhone());
        setCity(customer.getAddress().getCity());
        setCountry(customer.getAddress().getCity().getCountry());
    }

    /**
     * Performs validation of the {@link CustomerForm}. Displays errors to the
     * {@link User} if necessary.
     */
    public final void validateForm() {
        if (getName() == null || getName().isEmpty()) {
            setError("Please enter a name");
            setValid(false);
        } else if (getAddress() == null || getAddress().isEmpty()) {
            setError("Please enter an address");
            setValid(false);
        } else if (getAddress2() == null || getAddress2().isEmpty()) {
            setError("Please enter an address 2");
            setValid(false);
        } else if (getPostalCode() == null || getPostalCode().isEmpty()) {
            setError("Please enter a postal code");
            setValid(false);
        } else if (getPostalCode().length() < 5) {
            setError("Invalid postal code");
            setValid(false);
        } else if (getPhone() == null || getPhone().isEmpty()) {
            setError("Please enter a phone number");
            setValid(false);
        } else if (getPhone().length() < 14) {
            setError("Invalid phone number");
            setValid(false);
        } else if (getCity() == null) {
            setError("Please select a city");
            setValid(false);
        } else if (getCountry() == null) {
            setError("Please select a country");
            setValid(false);
        } else {
            setValid(true);
        }
    }
    
    private void submitForm() {
        Address customerAddress = null;
        Country customerCountry = getCountry();
        City customerCity = getCity();

        // If the country being submitted does not exist, add it to the database
        if (!countryDao.exists(customerCountry.getName())) {
            countryDao.add(customerCountry);
        }
        
        Optional<Country> maybeCountry = countryDao.findByName(customerCountry.getName());
        
        if (maybeCountry.isPresent()) {
            customerCountry = maybeCountry.get();
        } else {
            setError("Internal server error");
            setValid(false);
        }

        // If the city being submitted does not exist, add it to the database
        if (!cityDao.exists(customerCity.getName())) {
            customerCity.setCountry(customerCountry);
            cityDao.add(customerCity);
        }
        
        Optional<City> maybeCity = cityDao.findByName(customerCity.getName());
        
        if (maybeCity.isPresent()) {
            customerCity = maybeCity.get();
        } else {
            setError("Internal server error");
            setValid(false);
        }

        // If the address being submitted does not exist, add it to the database
        if (!addressDao.exists(getAddress(), getAddress2(), getPostalCode(), getPhone())) {
            addressDao.add(
                    new Address(
                            -1,
                            getAddress(),
                            getAddress2(),
                            getPostalCode(),
                            getPhone(),
                            customerCity,
                            SchedulingApplication.USER.getUserName(),
                            SchedulingApplication.USER.getUserName(),
                            Instant.now(),
                            Instant.now()
                    )
            );
        }
        
        Optional<Address> maybeAddress = addressDao.findByProperties(getAddress(), getAddress2(), getPostalCode(), getPhone(), customerCity.getId());
        
        if (maybeAddress.isPresent()) {
            customerAddress = maybeAddress.get();
        } else {
            setError("Internal server error");
            setValid(false);
        }
        
        if (isValid()) {
            getOnSubmit().accept(
                    new Customer(
                            customer != null ? customer.getId() : -1,
                            getName(),
                            isActive(),
                            customerAddress,
                            customer != null ? customer.getCreatedBy() : SchedulingApplication.USER.getUserName(),
                            SchedulingApplication.USER.getUserName(),
                            customer != null ? customer.getCreatedAt() : Instant.now(),
                            Instant.now()
                    )
            );
        } else {
            System.out.println(getError());
        }
    }
    
    @Override
    protected Skin<?> createDefaultSkin() {
        return new CustomerFormSkin(this);
    }
    
}
