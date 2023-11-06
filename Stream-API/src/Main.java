import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
	static List<Employee> employeeList = new ArrayList<>();
	static {
		Employee employee1 = new Employee("EMPALTI1", "Nagababu", "Software", 1800000);
		Employee employee2 = new Employee("EMPALTI2", "Naresh", "Software", 1400000);
		Employee employee3 = new Employee("EMPALTI3", "Suresh", "Senior Software", 2300000);
		Employee employee4 = new Employee("EMPALTI4", "Rajesh", "Senior Software", 3500000);
		Employee employee5 = new Employee("EMPALTI5", "Satish", "Manager", 3500000);
		employeeList.add(employee1);
		employeeList.add(employee2);
		employeeList.add(employee3);
		employeeList.add(employee4);
		employeeList.add(employee5);
	}

	public static void main(String[] args) {
		List<String> stringList = Arrays.asList("a", "b", "c", "aa", "abc", "a");
		// print duplicates from the list
		Set<String> uniqueSet = new HashSet<>();
		System.out.println(stringList.stream().filter(t -> !uniqueSet.add(t)).collect(Collectors.toSet()));
		System.out.println(stringList.stream()
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream().peek(t->
				System.out.println("intermediate result is----"+t))
				.filter(t -> t.getValue() > 1).map(t -> t.getKey()).collect(Collectors.toSet()));
		// find out all three character strings from given strings
		System.out.println(stringList.stream().collect(Collectors.groupingBy(String::length)).entrySet().stream()
				.filter(t -> t.getKey() == 3).flatMap(t -> t.getValue().stream()).collect(Collectors.toSet()));
		// sorted order
		System.out.println(stringList.stream().sorted().collect(Collectors.toList()));
		// descending order
		System.out.println(stringList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList()));
		String name = "nagababu";
		// remove repeating characters from the name
		System.out.println(Arrays.stream(name.split(""))
				.collect(Collectors.groupingBy(Function.identity(), Collectors.counting())).entrySet().stream()
				.filter(t -> t.getValue() == 1).map(t -> t.getKey()).collect(Collectors.toSet()));
		// partitioning strings based on the length
		System.out.println(stringList.stream().collect(Collectors.partitioningBy(t -> t.length() > 1)));
		// Converting list to Map
		Set<String> s = new HashSet<>();
		Map<String, Integer> map = stringList.stream().filter(t -> s.add(t))
				.collect(Collectors.toMap(Function.identity(), String::length));
		System.out.println(map);
		// sorting the map based on key
		System.out.println(map.entrySet().stream().sorted(Map.Entry.comparingByKey())
				.collect(Collectors.toMap(t -> t.getKey(), t -> t.getValue(), (t, u) -> t, LinkedHashMap::new)));
		// sorting the map based on value
		System.out.println(map.entrySet().stream().sorted(Map.Entry.comparingByValue())
				.collect(Collectors.toMap(t -> t.getKey(), t -> t.getValue(), (t, u) -> t, LinkedHashMap::new)));
		// Find Keys of second highest value in a map
		System.out.println(map.entrySet().stream()
				.collect(Collectors.groupingBy(Map.Entry::getValue,
						Collectors.mapping(Map.Entry::getKey, Collectors.toList())))
				.entrySet().stream().peek(t->System.out.println("intermedite result is---"+t))
				.sorted(Map.Entry.comparingByKey()).collect(Collectors.toList()).get(1));
		// Summary Statistics
		IntSummaryStatistics intSummary = IntStream.range(1, 10).summaryStatistics();
		System.out.println(intSummary.getSum() + "----" + intSummary.getAverage() + "----" + intSummary.getCount()
				+ "----" + intSummary.getMax() + "----" + intSummary.getMin());
		System.out.println(IntStream.range(1, 11).reduce(Integer::sum).getAsInt());
		System.out.println(IntStream.range(1, 11).reduce(0, (a, b) -> a + b));
		// nested map creation
		System.out.println(map.entrySet().stream().collect(
				Collectors.groupingBy(Map.Entry::getValue, Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
		// [-5,4,5,0,1,2,-2,3]
		// Find the pair of elements in list that gives sum as zero
		List<Integer> integerList = Arrays.asList(-5, 4, 5, 0, 1, 2, -2, 3);
		System.out.println(integerList.stream().filter(t -> t > 0).collect(Collectors.toList()).stream()
				.filter(t -> integerList.contains(-t)).collect(Collectors.toMap(t -> t, t -> -t)));
		// Sorting the Employees based on their salaries
		System.out.println(
				employeeList.stream().sorted(Comparator.comparing(Employee::getSalary)).collect(Collectors.toList()));
		// Sorting the Employees based on their salaries and designation
		System.out.println(employeeList.stream()
				.sorted(Comparator.comparing(Employee::getSalary).thenComparing(Employee::getDesignation))
				.collect(Collectors.toList()));
		// Grouping based on Designation
		System.out.println(employeeList.stream().collect(Collectors.groupingBy(Employee::getDesignation)));
		// Print the name of the employee whose salary is highest
		System.out.println(employeeList.stream().collect(
				Collectors.groupingBy(Employee::getSalary,
				Collectors.collectingAndThen(
				Collectors.toList(), e->e.stream()
				.sorted(Comparator.comparing(Employee::getSalary).reversed())
				.map(t->t.getEmployeeName())
				.collect(Collectors.toList())
				))).entrySet().stream()
				.sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
		        .map(t->t.getValue())
		        .findFirst().get());
		// Print the name of the employees whose salary is second highest based on their designation
		Map<String, String> topEmployeesByDesignation = employeeList.stream()
				.collect(Collectors.groupingBy(Employee::getDesignation,
						Collectors.collectingAndThen(Collectors.toList(),
								list -> list.stream().sorted(Comparator.comparing(Employee::getSalary).reversed())
										.map(t -> t.getEmployeeName()).skip(1).findFirst().orElse("No Employee Found"))));
		System.out.println("secondHighestSalryByDesignation----" + topEmployeesByDesignation);
		// Second Highest Salary in Software designation
		System.out.println(employeeList.stream().filter(t -> t.getDesignation().equalsIgnoreCase("Software"))
				.collect(Collectors.collectingAndThen(Collectors.toList(),list -> list.stream().sorted(Comparator.comparing(Employee::getSalary).reversed())
				.map(t -> t.getEmployeeName()).skip(1).findFirst().orElse("No Employee Found"))));
	    //Parallel Streaming
		/*
		 * long startTime = System.currentTimeMillis(); IntStream.rangeClosed(1,
		 * 10000).forEach(t->System.out.println(Thread.currentThread().getName())); long
		 * endTime = System.currentTimeMillis();
		 * System.out.println("Time taken to finish the sequential stream----"+(endTime-
		 * startTime)); long startTimeParallel = System.currentTimeMillis();
		 * IntStream.rangeClosed(1,
		 * 10000).parallel().forEach(t->System.out.println(Thread.currentThread().
		 * getName())); long endTimeParallel = System.currentTimeMillis();
		 * System.out.println("Time taken to finish the parallel stream----"+(
		 * endTimeParallel-startTimeParallel));
		 */
	}
}
