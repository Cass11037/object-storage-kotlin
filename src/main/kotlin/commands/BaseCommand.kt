abstract class BaseCommand  (
    private val name: String,
    private val descr: String,
) : Command {
    protected val collection: CollectionManager = CollectionManager("car.json");
    override fun getName(): String = name
    override fun getDescription(): String = descr
}