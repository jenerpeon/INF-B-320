/*
Copyright 2015 Shriram Mani, Ironpillars, Inc.

        Licensed under the Apache License, Version 2.0 (the "License");
        you may not use this file except in compliance with the License.
        You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

        Unless required by applicable law or agreed to in writing, software
        distributed under the License is distributed on an "AS IS" BASIS,
        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
        See the License for the specific language governing permissions and
        limitations under the License.
*/

package utils.com.ipillars.sorter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Shriram Mani, Ironpillars, Inc on 9/18/2015.
 */
public class SortHelper {

    private class Record {
        protected Integer index;
        protected List columns;

        protected Record(Integer index, List columns) {
            this.index = index;
            this.columns = columns;
        }

        protected void addSortableObject(Object sortableObject) {

            if (columns == null)
                columns = new ArrayList<>();

            columns.add(sortableObject);
        }

        protected List getColumns() {
            return columns;
        }

        protected void setColumns(List columns) {
            this.columns = columns;
        }

        protected Integer getIndex() {
            return index;
        }

        protected void setIndex(Integer index) {
            this.index = index;
        }

        @Override
        public String toString() {
            return "Record{" +
                    "index=" + index +
                    ", columns=" + columns +
                    '}';
        }
    }

    // Rows (List of records) with index in the list and a list of columns (data) to sort
    private List<Record> rows;

    // Keys (Names + Optionally descending flag + Optionally data type) on which the object is sorted
    private SortKeys sortKeys;

    // Result from the sort is a stream
    private Stream<Record> resultStream;

    private boolean dataSorted = false;

    /**
     * Add each row to the list before sorting
     * @param index - The index in the list being sorted
     * @param objects - Only those columns that needs to be sorted and in the order it should be sorted
     */
    public void addObjectsToSort(Integer index, List objects) {

        if (rows == null) {
            rows = new ArrayList<Record>();
        }

        rows.add(new Record(index, objects));
        dataSorted = false;
    }

    /**
     * Sort the list
     */
    public void sort() {

        // Check if the list is empty before sorting
        if (rows == null || rows.isEmpty()) {
            throw new RuntimeException("List is empty. Nothing to sort");
        }

        // Make sure the columns to sort on are set along with their class type
        if (sortKeys == null || sortKeys.getSortFields().isEmpty()) {

            buildSortKeysFromFirstRecord();

            if (sortKeys == null) {
                throw new RuntimeException("Cannot Sort without the Sort keys");
            }
        }

        // Create a chained comparator with all the data elements to compare
        Comparator<Record> finalComparator = null;

        // Build the comparators to compare and do the actual sorting at the end

        // Determine the data type for each key columns
        int index = 0;
        for (SortField sortField : sortKeys.getSortFields()) {

            // Get the class type from the first element, if caller didn't provide the classType
            if (sortField.getFieldClass() == null) {
                if (rows == null || rows.isEmpty()) {
                    throw new RuntimeException("There are no rows in the list to sort");
                } else if (rows.get(0).getColumns().size() <= index) {
                    throw new RuntimeException("There are more sort keys specified, which is more than the sort data provided");
                } else {
                    // Store the class type back to the SortField object so that it can be used later
                    sortField.setFieldClass(rows.get(0).getColumns().get(index).getClass());
                }
            }

            // Get the comparator based on the data type
            Comparator<Record> comparator = findComparator(sortField.getFieldClass(), index);

            // Check if the sort order should be reversed. True means reversed
            if (sortField.getDescending() != null && sortField.getDescending() == Boolean.TRUE) {
                comparator = comparator.reversed();
            }

            // Keep appending the comparator to the final Comparator
            if (finalComparator == null) {
                finalComparator = comparator;
            } else {
                // Append the comparator one after the other
                finalComparator = finalComparator.thenComparing(comparator);
            }
            index++;
        }

        // Sort with the aggregated comparator and get the resulting stream
        resultStream = rows.stream().sorted(finalComparator);
        dataSorted = true;
    }

    /**
     * If the records are sorted then
     * @return
     */
    private List<Record> getSortedDataList() {

        // Check if the data is sorted
        if (!dataSorted) {
            sort();
        }

        // Create a list of sorted records (sortedList) and add the rows to that list
        List<Record> sortedList = new ArrayList<Record>();

        // Convert the stream into List of indexes of records
        resultStream.forEach(data -> sortedList.add(new Record(data.getIndex(), data.getColumns())));

        return sortedList;
    }

    public List<Integer> getSortedIndexList() {

        // Check if the data is sorted
        if (!dataSorted) {
            sort();
        }

        List<Integer> retval = new ArrayList<Integer>();

        // Convert the stream into List of sorted indexes
        resultStream.forEach(data -> retval.add(data.getIndex()));

        return retval;
    }

    public Comparator findComparator(Class dataType, int index) {

        Comparator retval = null;

        if (dataType == String.class) {
            retval = getStringComparator(index);
        } else if (dataType == Boolean.class || dataType == Boolean.TYPE) {
            retval = getBooleanComparator(index);
        } else if (dataType == Byte.class || dataType == Byte.TYPE) {
            retval = getByteComparator(index);
        } else if (dataType == Short.class || dataType == Short.TYPE) {
            retval = getShortComparator(index);
        } else if (dataType == Integer.class || dataType == Integer.TYPE) {
            retval = getIntegerComparator(index);
        } else if (dataType == Long.class || dataType == Long.TYPE) {
            retval = getLongCompartor(index);
        } else if (dataType == Float.class || dataType == Float.TYPE) {
            retval = getFloatCompartor(index);
        } else if (dataType == Double.class || dataType == Double.TYPE) {
            retval = getDoubleComparator(index);
        } else if (dataType == Date.class) {
            retval = getDateComparator(index);
        } else {
            throw new RuntimeException("Don't know how to sort the data of type " + dataType);
        }

        return retval;
    }

    public Comparator getBooleanComparator(int index) {

        // Get the comparator using Java 8 lambda expression
        Comparator<Record> retval = (object1, object2) -> Boolean.compare((Boolean) object1.getColumns().get(index),
                        (Boolean) object2.getColumns().get(index));
        return retval;
    }

    public Comparator getByteComparator(int index) {

        // Get the comparator using Java 8 lambda expression
        Comparator<Record> retval = (object1, object2) -> Byte.compare((Byte) object1.getColumns().get(index),
                        (Byte) object2.getColumns().get(index));
        return retval;
    }

    public Comparator getDoubleComparator(int index) {

        // Get the comparator using Java 8 lambda expression
        Comparator<Record> retval = (object1, object2) -> Double.compare((Double) object1.getColumns().get(index),
                        (Double) object2.getColumns().get(index));
        return retval;
    }

    public Comparator getIntegerComparator(int index) {

        // Get the comparator using Java 8 lambda expression
        Comparator<Record> retval = (object1, object2) -> Integer.compare((Integer) object1.getColumns().get(index),
                        (Integer) object2.getColumns().get(index));
        return retval;
    }

    public Comparator getLongCompartor(int index) {

        // Get the comparator using Java 8 lambda expression
        Comparator<Record> retval = (object1, object2) -> Long.compare((Long) object1.getColumns().get(index),
                        (Long) object2.getColumns().get(index));
        return retval;
    }

    public Comparator getShortComparator(int index) {

        // Get the comparator using Java 8 lambda expression
        Comparator<Record> retval = (object1, object2) -> Short.compare((Short) object1.getColumns().get(index),
                        (Short) object2.getColumns().get(index));
        return retval;
    }

    public Comparator getFloatCompartor(int index) {

        // Get the comparator using Java 8 lambda expression
        Comparator<Record> retval = (object1, object2) -> Float.compare((Float) object1.getColumns().get(index),
                        (Float) object2.getColumns().get(index));
        return retval;
    }

    public Comparator getStringComparator(int index) {

        // Get the comparator using Java 8 lambda expression
        Comparator<Record> retval = (object1, object2) -> ((String) object1.getColumns().get(index)).compareTo(
                (String) object2.getColumns().get(index));
        return retval;
    }

    public Comparator getDateComparator(int index) {

        // Get the comparator using Java 8 lambda expression
        Comparator<Record> retval = (object1, object2) -> ((Date) object1.getColumns().get(index)).compareTo(
                        (Date) object2.getColumns().get(index));
        return retval;
    }

    /**
     * Depending on the number of columns it will create a list of keys with some fake name.
     * Also the method determines the class type of the data
     */
    private void buildSortKeysFromFirstRecord() {

        if (sortKeys == null) {
            sortKeys = new SortKeys();
        }

        int index = 0;
        for (Object firstValueToSort : rows.get(0).getColumns()) {
            // Get the class type from the first element
            Class aClass = firstValueToSort.getClass();
            sortKeys.addField("Column" + index++, aClass);

        }
    }

    /**
     * Sort key information (name of the field, Class type of the field and descending indicator)
     * @param sortKeys
     */
    public void setSortKeys(SortKeys sortKeys) {
        this.sortKeys = sortKeys;
    }

    public void dumpDataList() {
        System.out.println(rows);
    }
}
