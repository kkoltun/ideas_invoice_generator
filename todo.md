# Readme

A PoC of the service used to record and generate invoices.

## Todo

Technical:
1. Tests
2. Better handling of the database connection, closing of the resources.
3. NBP service caching responses in DB.
4. Update the "created" and "lastupdated" fields correctly and automatically
5. Limit the number public classes and methods
6. Add linting tool, -Wall and spotbugs.
7. Add exception mapping for controllers
8. Central point for all invoices (international and national)? How to handle this?

Business:
1. Can an international invoice & national invoice with the same number exist in the system?
2. The same debtor has to be duplicated if they have international & national invoices (PL[NIP] and [NIP]).
3. Removing company with any invoices should not be possible.

Queue:
1. New invoice -> add to queue.

Generator:
1. New invoice message -> generator gets this and generates a PDF -> saves it somewhere?
2. Get the invoice PDF -> add to queue synchronously -> either get the PDF or generate it and get.

Actor totals service:
1. On startup launch and calculate actor totals.
2. New invoice message -> add to totals.