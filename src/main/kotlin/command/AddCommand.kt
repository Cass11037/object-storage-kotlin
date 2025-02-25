class AddCommand(
    private val collection: CollectionManager,
//    private val reader: VehicleReader
) : Command {
    override val name = "add"
    override val description = "Add new vehicle to collection"

    override fun execute(args: List<String>): Boolean {
//        val vehicle = reader.readVehicle()
//        collection.addVehicle(vehicle)
//        println("Vehicle added with ID: ${vehicle.id}")
        return true
    }
}