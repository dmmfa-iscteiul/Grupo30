package G30_82664.Covid_Query;

public class Region {
	private String name;

	/**
	 * Creates a Region instance with the given name.
	 * @param name name of the region.
	 */
	public Region(String name) {
		super();
		this.name = name;
	}

	/**
	 * Get the name of the region.
	 * @return the region name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Set the name of the region.
	 * @param name the new name for the region.
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return String.format("Region [name=%s]", name);
	}
}
