package G30_82664.Covid_Query;

import java.util.ArrayList;
import java.util.List;

public class RegionService {
	private static List<Region> regions = new ArrayList<Region>();

	/**
	 * Get the list of regions.
	 * @return the list of regions.
	 */
	public List<Region> getRegions() {
		return regions;
	}

	/**
	 * Appends the specified region to the end of the list.
	 * @param r the region to add to the end of the list. 
	 */
	public void addRegion(Region r) {
		regions.add(r);
	}

	/**
	 * Removes the first occurrence of the specified region from the list.
	 * If the list does not contain the region, it is unchanged.
	 * @param r the region to remove.
	 */
	public void deleteRegion(Region r) {
		regions.remove(r);
	}

	/**
	 * Removes all of the regions from this list.
	 */
	public void deleteAll() {
		regions.clear();
	}
}
