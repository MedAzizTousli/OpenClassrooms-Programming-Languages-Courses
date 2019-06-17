/* Question 1 */
SELECT first_name, last_name
FROM employees

/* Question 2 */
SELECT *
FROM employees
WHERE hire_date > '1999-08-01'

/* Question 3 */
SELECT * 
FROM dept_emp
UNION
SELECT *
FROM dept_manager

/* Question 4 */
SELECT *
FROM salaries
WHERE emp_no = 499593

/* Question 5 */
SELECT *
FROM dept_emp
WHERE emp_no = 499902

/* Question 6 */
SELECT count(*)
FROM employees
WHERE last_name = "Gewali"
