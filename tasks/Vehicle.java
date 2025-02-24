public class Vehicle {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.util.Date creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private double enginePower; //Значение поля должно быть больше 0
    private Double distanceTravelled; //Поле может быть null, Значение поля должно быть больше 0
    private VehicleType type; //Поле может быть null
    private FuelType fuelType; //Поле может быть null
}
