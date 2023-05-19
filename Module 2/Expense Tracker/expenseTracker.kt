/*
This program is an Expense Tracker application designed to help users track their expenses, 
categorize spending, generate reports, set budgets, and provide visual representations of 
spending patterns over time. The program allows users to interact with the application by 
selecting various options.

The program makes use of the Expense data class to represent individual expenses, storing 
information such as the amount, category, and date of each expense. The ExpenseTracker class 
encapsulates the logic for managing expenses, including adding expenses, calculating the total 
expense, retrieving expenses by category, generating expense reports, and saving/loading data 
to/from a file.

The user is prompted to enter their username and expense data is loaded from a file if available. 
The main program runs in a loop, presenting a menu of options for the user to choose from. The user 
can add an expense, view the total expense, view expenses by category, generate an expense report, 
save expense data to a file, or exit the program.
*/ 

import java.io.File // Provides classes for file operations
import java.text.DecimalFormat // Formats decimal numbers
import java.time.LocalDate // Represents a date (year, month, day)

// Data class representing an expense
data class Expense(val amount: Double, val category: String, val date: LocalDate)

// ExpenseTracker class to manage expenses
class ExpenseTracker(private val username: String) {
    private val expenses: MutableList<Expense> = mutableListOf()

    // Add a new expense to the tracker
    fun addExpense(amount: Double, category: String, date: LocalDate) {
        val expense = Expense(amount, category.lowercase(), date)
        expenses.add(expense)
    }

    // Calculate and return the total expense
    fun getTotalExpense(): Double {
        return expenses.sumOf { it.amount }
    }

    // Retrieve expenses by category
    fun getExpensesByCategory(category: String): List<Expense> {
        val lowerCaseCategory = category.lowercase()
        return expenses.filter { it.category.lowercase() == lowerCaseCategory }
    }

    // Generate an expense report, mapping categories to total amounts
    fun generateExpenseReport(): Map<String, Double> {
        val report = mutableMapOf<String, Double>()
        for (expense in expenses) {
            val category = expense.category
            val amount = report.getOrDefault(category, 0.0)
            report[category] = amount + expense.amount
        }
        return report
    }

    // Save expense data to a file
    fun saveToFile() {
        val file = File("record.txt")
        val content = StringBuilder()
        val decimalFormat = DecimalFormat("$#0.00")

        for (expense in expenses) {
            val formattedAmount = decimalFormat.format(expense.amount)
            content.append("$formattedAmount,${expense.category},${expense.date}\n")
        }
        file.writeText(content.toString())
        println("Expense data saved to file.")
    }

    // Load expense data from a file
    fun loadFromFile() {
        val file = File("record.txt")
        if (file.exists()) {
            val lines = file.readLines()
            for (line in lines) {
                val parts = line.split(",")
                if (parts.size == 3) {
                    val amount = parts[0].substring(1).toDoubleOrNull()
                    val category = parts[1].lowercase()
                    val date = LocalDate.parse(parts[2])
                    if (amount != null) {
                        val expense = Expense(amount, category, date)
                        expenses.add(expense)
                    }
                }
            }
            println("Expense data loaded from file.")
        } else {
            println("No expense data file found.")
        }
    }

    // Delete expenses by category
    fun deleteExpensesByCategory(category: String): Boolean {
        val lowerCaseCategory = category.lowercase()
        val expensesToDelete = expenses.filter { it.category.lowercase() == lowerCaseCategory }
        if (expensesToDelete.isNotEmpty()) {
            expenses.removeAll(expensesToDelete)
            println("Expenses with category '$category' have been deleted.")
            return true
        }
        return false
    }

    // Check if the expense tracker is empty
    fun isEmpty(): Boolean {
        return expenses.isEmpty()
    }
}

// Main programjoe
fun main() {
    println("Expense Tracker Application")
    println("Please enter your username:")
    val username = readLine() ?: ""

    val expenseTracker = ExpenseTracker(username)
    expenseTracker.loadFromFile()

    var running = true
    while (running) {
        println()
        println("1. Add Expense")
        println("2. View Total Expense")
        println("3. View Expenses by Category")
        println("4. Generate Expense Report")
        println("5. Delete Expenses by Category")
        println("6. Save Expense Data to File")
        println("7. Exit")
        println("Enter your choice:")

        val choice = readLine()?.toIntOrNull() ?: 0
        println()

        when (choice) {
            1 -> {
                // Add Expense
                var validExpense = false
                var amount = 0.0
                while (!validExpense) {
                    println("Enter expense amount:")
                    val input = readLine()
                    if (input != null && input.matches(Regex("-?\\d+(\\.\\d+)?"))) {
                        amount = input.toDouble()
                        validExpense = true
                    } else {
                        println("Invalid expense amount. Please enter a valid number.")
                    }
                }
            
                println("Enter expense category:")
                val category = readLine()?.lowercase() ?: ""
            
                val date = LocalDate.now()
            
                expenseTracker.addExpense(amount, category, date)
                println("Expense added successfully.")
            }
            2 -> {
                // View Total Expense
                val totalExpense = expenseTracker.getTotalExpense()
                val decimalFormat = DecimalFormat("$#,##0.00")
                val formattedTotalExpense = decimalFormat.format(totalExpense)
                println("Total Expense: $formattedTotalExpense")
            }
            3 -> {
                // View Expenses by Category
                println("Enter category:")
                val category = readLine()?.lowercase() ?: ""

                if (expenseTracker.isEmpty()) {
                    println("Expense data is empty. Returning to the main menu.")
                    continue
                }

                val expensesByCategory = expenseTracker.getExpensesByCategory(category)
                if (expensesByCategory.isEmpty()) {
                    println("No expenses found for category '$category'.")
                } else {
                    println("Expenses for Category '$category':")
                    val decimalFormat = DecimalFormat("$#,##0.00")
                    for (expense in expensesByCategory) {
                        println("Amount: ${decimalFormat.format(expense.amount)}, Date: ${expense.date}")
                    }
                }
            }
            4 -> {
                // Generate Expense Report
                val report = expenseTracker.generateExpenseReport()
                if (report.isEmpty()) {
                    println("No expenses found. Expense report is empty.")
                } else {
                    println("Expense Report:")
                    val decimalFormat = DecimalFormat("$#,##0.00")
                    for ((category, amount) in report) {
                        println("Category: $category, Amount: ${decimalFormat.format(amount)}")
                    }
                }
            }
            5 -> {
                // Delete Expenses by Category
                println("Enter category:")
                val category = readLine()?.lowercase() ?: ""

                val success = expenseTracker.deleteExpensesByCategory(category)
                if (!success) {
                    println("No expenses found for category '$category'.")
                }
            }
            6 -> {
                // Save Expense Data to File
                expenseTracker.saveToFile()
            }
            7 -> {
                // Exit
                expenseTracker.saveToFile()
                running = false
            }
            else -> {
                println("Invalid choice. Please try again.")
            }
        }
    }
}
