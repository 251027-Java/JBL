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
    JOIN album ON artist.artist_id = album.artist_id;

-- All songs of the rock genre
SELECT *
FROM track
    JOIN genre ON track.genre_id = (
        SELECT genre.genre_id
        FROM genre
        WHERE
            genre.name = 'Rock'
    );

-- Show all invoices of customers from brazil (mailing address not billing)
SELECT *
FROM invoice
    JOIN customer ON invoice.customer_id = customer.customer_id
WHERE
    country = 'Brazil';

-- Show all invoices together with the name of the sales agent for each one
SELECT invoice.invoice_id, employee.*
FROM
    invoice
    JOIN customer ON invoice.customer_id = customer.customer_id
    JOIN employee ON customer.support_rep_id = employee.employee_id;

-- Which sales agent made the most sales in 2009?
SELECT SUM(invoice.total) as sum_total, employee.*
FROM
    invoice
    JOIN customer ON invoice.customer_id = customer.customer_id
    JOIN employee ON customer.support_rep_id = employee.employee_id
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
    JOIN customer ON employee.employee_id = customer.support_rep_id
GROUP BY
    employee.employee_id;

-- Which track was purchased the most in 2010?
SELECT COUNT(track.track_id) as count_track, track.*
FROM
    invoice_line
    JOIN track ON invoice_line.track_id = track.track_id
    JOIN invoice ON invoice_line.invoice_id = invoice.invoice_id
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
    JOIN track ON invoice_line.track_id = track.track_id
    JOIN album ON track.album_id = album.album_id
    JOIN artist ON artist.artist_id = album.artist_id
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
    JOIN initials_table ON customer.customer_id = initials_table.customer_id
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
    JOIN customer ON invoice.customer_id = customer.customer_id
GROUP BY
    customer.customer_id
ORDER BY sum_total DESC
LIMIT 1;

-- Who is the highest spending customer?
SELECT customer.*, SUM(invoice.total) as sum_total
FROM invoice
    JOIN customer ON invoice.customer_id = customer.customer_id
GROUP BY
    customer.customer_id
ORDER BY sum_total DESC
LIMIT 1;

-- Return the email and full name of of all customers who listen to Rock.
SELECT customer.email, customer.first_name, customer.last_name
FROM
    customer
    JOIN invoice ON invoice.customer_id = customer.customer_id
    JOIN invoice_line ON invoice_line.invoice_id = invoice.invoice_id
    JOIN track ON invoice_line.track_id = track.track_id
    JOIN genre ON track.genre_id = genre.genre_id
GROUP BY
    customer.customer_id
HAVING
    'Rock' = ANY (array_agg(genre.name));

-- Which artist has written the most Rock songs?
SELECT artist.name, genre.name, COUNT(genre.name)
FROM
    artist
    JOIN album ON album.artist_id = artist.artist_id
    JOIN track ON track.album_id = album.album_id
    JOIN genre ON genre.genre_id = track.genre_id
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
    JOIN album ON album.artist_id = artist.artist_id
    JOIN track ON track.album_id = album.album_id
    JOIN genre ON genre.genre_id = track.genre_id
    JOIN invoice_line ON invoice_line.track_id = track.track_id
GROUP BY
    artist.name
ORDER BY revanue DESC;

-- ADVANCED CHALLENGES
-- solve these with a mixture of joins, subqueries, CTE, and set operators.
-- solve at least one of them in two different ways, and see if the execution
-- plan for them is the same, or different.

-- 1. which artists did not make any albums at all?
SELECT *
FROM artist
WHERE
    artist.artist_id NOT IN (
        SELECT album.artist_id
        FROM album
    );

-- 2. which artists did not record any tracks of the Latin genre?
WITH
    temp_data AS (
        SELECT DISTINCT
            album.artist_id
        FROM track
            JOIN genre ON track.genre_id = genre.genre_id
            JOIN album ON album.album_id = track.album_id
        WHERE
            genre.name != 'Latin'
    )
SELECT artist.*
FROM artist
    JOIN temp_data ON artist.artist_id = temp_data.artist_id;

-- 3. which video track has the longest length? (use media type table)
SELECT track.*
FROM track
    JOIN media_type ON media_type.media_type_id = track.media_type_id
WHERE
    media_type.name ILIKE '%video%'
ORDER BY track.milliseconds DESC;

-- 4. boss employee (the one who reports to nobody)
SELECT * FROM employee WHERE employee.reports_to IS NULL;

-- 5. how many audio tracks were bought by German customers, and what was
--    the total price paid for them?
SELECT COUNT(*), SUM(
        invoice_line.quantity * invoice_line.unit_price
    )
FROM
    invoice_line
    JOIN track ON invoice_line.track_id = track.track_id
    JOIN invoice ON invoice.invoice_id = invoice_line.invoice_id
    JOIN customer ON customer.customer_id = invoice.customer_id
    JOIN media_type ON track.media_type_id = media_type.media_type_id
WHERE
    media_type.name ILIKE '%audio%'
GROUP BY
    customer.country
HAVING
    customer.country = 'Germany';

-- 6. list the names and countries of the customers supported by an employee
--    who was hired younger than 35.

SELECT customer.first_name, customer.last_name, customer.country
FROM customer
    JOIN employee ON customer.support_rep_id = employee.employee_id
WHERE
    EXTRACT(
        YEAR
        FROM age (
                employee.hire_date, employee.birth_date
            )
    ) < 35;

-- DML exercises

-- 1. insert two new records into the employee table.
INSERT INTO
    employee (
        "last_name",
        "first_name",
        "title",
        "reports_to",
        "birth_date",
        "hire_date",
        "address",
        "city",
        "state",
        "country",
        "postal_code",
        "phone",
        "fax",
        "email"
    )
VALUES (
        'wojlr',
        'uxezs',
        'ymldj',
        2,
        '1984-06-29 07:43:22',
        '1980-06-09 04:00:31',
        'kewrv',
        'nvrxo',
        'flcrc',
        'chgik',
        'vcpdd',
        'oreij',
        'hifvr',
        'bviqp'
    ),
    (
        'wttjb',
        'colmd',
        'xtsnt',
        2,
        '1978-09-15 04:20:13',
        '1988-11-25 13:49:28',
        'svnyc',
        'pptgs',
        'soxvy',
        'ihzuw',
        'synlg',
        'otnsc',
        'giidf',
        'blshj'
    );

-- 2. insert two new records into the tracks table.
INSERT INTO
    track (
        "name",
        "album_id",
        "media_type_id",
        "genre_id",
        "composer",
        "milliseconds",
        "bytes",
        "unit_price"
    )
VALUES (
        'rfjkm',
        66,
        4,
        20,
        'inxue',
        304,
        411,
        433
    ),
    (
        'oflmd',
        134,
        2,
        22,
        'ewlum',
        327,
        31,
        10
    );

-- 3. update customer Aaron Mitchell's name to Robert Walter
UPDATE customer
SET
    first_name = 'Robert',
    last_name = 'Walter'
WHERE
    customer.first_name = 'Aaron'
    AND customer.last_name = 'Mitchell';

-- 4. delete one of the employees you inserted.
DELETE FROM employee WHERE last_name = 'wojlr';

-- 5. delete customer Robert Walter.
ALTER TABLE invoice_line DROP CONSTRAINT fk_invoice_line_invoice_id;

ALTER TABLE "invoice_line"
ADD CONSTRAINT "fk_invoice_line_invoice_id" FOREIGN KEY (invoice_id) REFERENCES invoice (invoice_id) ON DELETE CASCADE;

ALTER TABLE invoice DROP CONSTRAINT fk_invoice_customer_id;

ALTER TABLE "invoice"
ADD CONSTRAINT "fk_invoice_customer_id" FOREIGN KEY (customer_id) REFERENCES customer (customer_id) ON DELETE CASCADE;

DELETE FROM customer
WHERE
    first_name = 'Robert'
    AND last_name = 'Walter';