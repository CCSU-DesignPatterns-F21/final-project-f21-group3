package com.group3.racingbot.inventory.filter;

import com.group3.racingbot.inventory.Iterator;

/**
 * Applies the quality filter to whatever inventory iterator is passed into it. This will filter out items which don't match the criteria for the specified quality.
 * @author Nick Sabia
 *
 * @param <T>
 */
public class QualityFilter<T extends MaterialFilterable> extends IteratorDecorator<T> {
	private Quality quality;
	private FilterOperation operation;
	
	/**
	 * Applies the quality filter to whatever inventory iterator is passed into it.
	 * @param iterator the iterator which is to be decorated
	 * @param op the logical operator
	 * @param quality the quality of the material object to filter by
	 */
	public QualityFilter(Iterator<T> iterator, FilterOperation op, Quality quality) {
		super(iterator);
		this.quality = quality;
		this.operation = op;
	}
	
	@Override
	public T next() {
		T item = super.getIterator().next();
		boolean itemMatchesContraints = false;
		switch (this.operation) {
			case IS_GREATER_THAN:
				itemMatchesContraints = item != null 
					&& item.getQuality().getQuality() > this.quality.getQuality();
				break;
			case IS_LESS_THAN:
				itemMatchesContraints = item != null 
					&& item.getQuality().getQuality() < this.quality.getQuality();
				break;
			case IS_EQUAL:
				itemMatchesContraints = item != null 
					&& item.getQuality().getQuality() == this.quality.getQuality();
				break;
			case IS_NOT_EQUAL:
				itemMatchesContraints = item != null 
					&& item.getQuality().getQuality() != this.quality.getQuality();
				break;
			default:
				System.out.println("QualityFilter: Invalid operator supplied.");
				break;
		}
		if (!itemMatchesContraints) {
			// If the item doesn't match the given criteria, 
			// we don't want to return this as a result. 
			item = null;
		}
		return item;
	}
	
	@Override
	public String getCriteria() {
		return this.operation.toString().toLowerCase() + " " + this.quality.toString().toLowerCase();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.quality.hashCode();
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) { return false; }
		if (this == other) { return true; } // Same instance 
		else if (other instanceof QualityFilter) {
			QualityFilter<?> otherObj = (QualityFilter<?>) other;
			if (this.getCriteria().equals(otherObj.getCriteria())) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "QualityFilter which filters for " + this.quality;
	}
}
