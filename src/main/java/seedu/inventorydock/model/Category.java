

    public void setName(String name) {
        this.name = name;
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
        logger.log(Level.INFO, "Added item '" + item.getName() + "' to category '" + name + "'.");
    }

    public Item getItem(int index) {
        return items.get(index);
    }

    public int getItemCount() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    /**
     * Removes the item at the specified zero-based index.
     *
     * @param index Zero-based index of the item to remove.
     * @throws IndexOutOfBoundsException If the index is out of range.
     */
    public void removeItem(int index) {
        if (index < 0 || index >= items.size()) {
            logger.log(Level.WARNING, "Invalid item removal index " + index + " for category '" + name + "'.");
            throw new IndexOutOfBoundsException("Invalid item index");
        }
        logger.log(Level.INFO, "Removed item '" + items.get(index).getName() + "' from category '" + name + "'.");
        items.remove(index);
    }

    /**
     * Returns the item whose name matches the specified name, ignoring case.
     *
     * @param itemName Name of the item to find.
     * @return Matching item, or {@code null} if no item matches.
     */
    public Item findItemByName(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Category: ").append(name).append("\n");
        for (int i = 0; i < items.size(); i++) {
            sb.append("  ").append(i + 1).append(". ").append(items.get(i)).append("\n");
        }
        return sb.toString();
    }
}
