/* Question 1 */
SELECT last_name
FROM employees
GROUP BY last_name
HAVING count(last_name) > 200 ;

/* Question 2 */
SELECT salary
FROM salaries
WHERE from_date 
IN
  (SELECT from_date
  FROM salaries
  WHERE from_date > '1999-08-20')

/* Question 3 */
SELECT *
FROM employees
ORDER BY birth_date
LIMIT 5

/* Question 4 */
SELECT *
FROM employees
WHERE last_name LIKE '_s%'
