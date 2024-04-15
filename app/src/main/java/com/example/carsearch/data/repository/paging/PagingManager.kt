package com.example.carsearch.data.repository.paging

class PagingManager(val pageSize: Int = 10) {
    private var nextPage = 0
    private var totalPages = 100

    /**
     * Returns the current page to fetch and ensures it doesn't exceed total pages.
     *
     * @throws IllegalStateException if the next page exceeds the total pages allowed.
     * @return the next page number.
     */
    fun nextPage(): Int {
        if (nextPage >= totalPages) {
            throw IllegalStateException("Cannot fetch page $nextPage as it exceeds the total page limit of $totalPages.")
        }
        return nextPage
    }

    /**
     * Sets the total number of pages available.
     *
     * @param totalPages the total number of pages.
     */
    fun setTotalPages(totalPages: Int) {
        this.totalPages = totalPages
    }

    /**
     * Increments the counter for the next page.
     */
    fun updateNextPage() {
        nextPage++
    }
}