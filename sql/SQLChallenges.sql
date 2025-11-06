-- Parking Lot*******
-- *                *
-- *                *
--- *****************

-- SETUP:
-- Create a database server (docker)
-- $ docker run --name some-postgres -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 -d postgres
-- Connect to the server (Azure Data Studio / Database extension)
-- Test your connection with a simple query (like a select)
-- Execute the Chinook database (from the Chinook_pg.sql file to create Chinook resources in your server)

SELECT * FROM actor;

-- Comment can be done single line with --
-- Comment can be done multi line with /* */

/*
DQL - Data Query Language
Keywords:

SELECT - retrieve data, select the columns from the resulting set
FROM - the table(s) to retrieve data from
WHERE - a conditional filter of the data
GROUP BY - group the data based on one or more columns
HAVING - a conditional filter of the grouped data
ORDER BY - sort the data
*/

SELECT * FROM actor;

SELECT last_name FROM actor;

SELECT * FROM actor WHERE first_name = 'Morgan';

select * from actor where first_name = 'John';

-- BASIC CHALLENGES
-- List all customers (full name, customer id, and country) who are not in the USA
SELECT
    first_name,
    last_name,
    customer_id,
    country
FROM customer
WHERE
    country != 'USA';

-- List all customers from Brazil
SELECT * FROM customer WHERE country = 'Brazil';

-- List all sales agents
SELECT * FROM employee WHERE title = 'Sales Support Agent';

-- Retrieve a list of all countries in billing addresses on invoices
SELECT DISTINCT billing_country FROM invoice;

-- Retrieve how many invoices there were in 2009, and what was the sales total for that year?
SELECT COUNT(*), SUM(total)
FROM invoice
WHERE
    EXTRACT(
        YEAR
        FROM invoice.invoice_date
    ) = 2009;

-- (challenge: find the invoice count sales total for every year using one query)
SELECT EXTRACT(
        YEAR
        FROM invoice.invoice_date
    ) AS invoice_year, SUM(total) as sum_total
FROM invoice
GROUP BY
    invoice_year;

-- how many line items were there for invoice #37
SELECT COUNT(*) FROM invoice_line WHERE invoice_id = 37;

-- how many invoices per country? BillingCountry  # of invoices -
SELECT billing_country, COUNT(billing_country)
FROM invoice
GROUP BY
    billing_country;

-- Retrieve the total sales per country, ordered by the highest total sales first.
SELECT billing_country, SUM(total) as sum_total
FROM invoice
GROUP BY
    billing_country
ORDER BY sum_total DESC;

-- JOINS CHALLENGES
-- Every Album by Artist
SELECT artist.name, album.title
FROM artist
    INNER JOIN album ON artist.artist_id = album.artist_id;

-- All songs of the rock genre
SELECT *
FROM track
    INNER JOIN genre ON track.genre_id = (
        SELECT genre.genre_id
        FROM genre
        WHERE
            genre.name = 'Rock'
    );

-- Show all invoices of customers from brazil (mailing address not billing)
SELECT *
FROM invoice
    INNER JOIN customer ON invoice.customer_id = customer.customer_id
WHERE
    country = 'Brazil';

-- Show all invoices together with the name of the sales agent for each one
SELECT invoice.invoice_id, employee.*
FROM
    invoice
    INNER JOIN customer ON invoice.customer_id = customer.customer_id
    INNER JOIN employee ON customer.support_rep_id = employee.employee_id;

-- Which sales agent made the most sales in 2009?
SELECT SUM(invoice.total) as sum_total, employee.*
FROM
    invoice
    INNER JOIN customer ON invoice.customer_id = customer.customer_id
    INNER JOIN employee ON customer.support_rep_id = employee.employee_id
WHERE
    EXTRACT(
        YEAR
        FROM invoice.invoice_date
    ) = 2009
GROUP BY
    employee.employee_id
ORDER BY sum_total DESC;

-- How many customers are assigned to each sales agent?
SELECT employee.employee_id, COUNT(employee.employee_id)
FROM employee
    INNER JOIN customer ON employee.employee_id = customer.support_rep_id
GROUP BY
    employee.employee_id;

-- Which track was purchased the most in 2010?
SELECT COUNT(track.track_id) as count_track, track.*
FROM
    invoice_line
    INNER JOIN track ON invoice_line.track_id = track.track_id
    INNER JOIN invoice ON invoice_line.invoice_id = invoice.invoice_id
WHERE
    EXTRACT(
        YEAR
        FROM invoice.invoice_date
    ) = 2010
GROUP BY
    track.track_id
ORDER BY count_track DESC;

-- Show the top three best selling artists.
SELECT artist.*, SUM(
        invoice_line.quantity * invoice_line.unit_price
    ) as sum_price
FROM
    invoice_line
    INNER JOIN track ON invoice_line.track_id = track.track_id
    INNER JOIN album ON track.album_id = album.album_id
    INNER JOIN artist ON artist.artist_id = album.artist_id
GROUP BY
    artist.artist_id
ORDER BY sum_price DESC
LIMIT 3;

-- Which customers have the same initials as at least one other customer?
WITH
    initials_table AS (
        SELECT customer_id, LEFT(first_name, 1) || LEFT(last_name, 1) AS initials
        FROM customer
    ),
    intitials_count AS (
        SELECT initials, COUNT(initials) as initial_count
        FROM initials_table
        GROUP BY
            initials_table.initials
    )
SELECT initials_table.initials, customer.*
FROM customer
    INNER JOIN initials_table ON customer.customer_id = initials_table.customer_id
WHERE
    initials_table.initials IN (
        SELECT intitials_count.initials
        FROM intitials_count
        WHERE
            intitials_count.initial_count > 1
    );

-- Which countries have the most invoices?
SELECT invoice.billing_country, COUNT(invoice.billing_country) as count_country
FROM invoice
GROUP BY
    invoice.billing_country
ORDER BY count_country DESC;

-- Which city has the customer with the highest sales total?
SELECT customer.customer_id, customer.city, SUM(invoice.total) as sum_total
FROM invoice
    INNER JOIN customer ON invoice.customer_id = customer.customer_id
GROUP BY
    customer.customer_id
ORDER BY sum_total DESC
LIMIT 1;

-- Who is the highest spending customer?
SELECT customer.*, SUM(invoice.total) as sum_total
FROM invoice
    INNER JOIN customer ON invoice.customer_id = customer.customer_id
GROUP BY
    customer.customer_id
ORDER BY sum_total DESC
LIMIT 1;

-- Return the email and full name of of all customers who listen to Rock.
SELECT customer.email, customer.first_name, customer.last_name
FROM
    customer
    INNER JOIN invoice ON invoice.customer_id = customer.customer_id
    INNER JOIN invoice_line ON invoice_line.invoice_id = invoice.invoice_id
    INNER JOIN track ON invoice_line.track_id = track.track_id
    INNER JOIN genre ON track.genre_id = genre.genre_id
GROUP BY
    customer.customer_id
HAVING
    'Rock' = ANY (array_agg(genre.name));

-- Which artist has written the most Rock songs?
SELECT artist.name, genre.name, COUNT(genre.name)
FROM
    artist
    INNER JOIN album ON album.artist_id = artist.artist_id
    INNER JOIN track ON track.album_id = album.album_id
    INNER JOIN genre ON genre.genre_id = track.genre_id
GROUP BY
    genre.name,
    artist.name
HAVING
    genre.name = 'Rock'
ORDER BY COUNT(genre.name) DESC
LIMIT 1;

-- Which artist has generated the most revenue?
SELECT artist.name, SUM(
        invoice_line.quantity * invoice_line.unit_price
    ) as revanue
FROM
    artist
    INNER JOIN album ON album.artist_id = artist.artist_id
    INNER JOIN track ON track.album_id = album.album_id
    INNER JOIN genre ON genre.genre_id = track.genre_id
    INNER JOIN invoice_line ON invoice_line.track_id = track.track_id
GROUP BY
    artist.name
ORDER BY revanue DESC;

-- ADVANCED CHALLENGES
-- solve these with a mixture of joins, subqueries, CTE, and set operators.
-- solve at least one of them in two different ways, and see if the execution
-- plan for them is the same, or different.

-- 1. which artists did not make any albums at all?

-- 2. which artists did not record any tracks of the Latin genre?

-- 3. which video track has the longest length? (use media type table)

-- 4. boss employee (the one who reports to nobody)

-- 5. how many audio tracks were bought by German customers, and what was
--    the total price paid for them?

-- 6. list the names and countries of the customers supported by an employee
--    who was hired younger than 35.

-- DML exercises

-- 1. insert two new records into the employee table.

-- 2. insert two new records into the tracks table.

-- 3. update customer Aaron Mitchell's name to Robert Walter

-- 4. delete one of the employees you inserted.

-- 5. delete customer Robert Walter.