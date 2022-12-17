# Readme

A PoC of the service used to record and generate invoices.

## Todo

Technical:
1. NBP service caching responses in DB; configure timeouts.
2. Update the "created" and "lastupdated" fields correctly and automatically
3. Limit the number public classes and methods
4. Add linting tool, -Wall and spotbugs.
5. Add exception mapping for controllers
6. Central point for all invoices (international and national)? How to handle this?
7. Specify accounts in the DB instead of hardcoding them in the invoice.
8. Put the db credentials somewhere safe.

Tests:
1. Tests with test containers.
2. PDF and other exporters as a separate service, everything ran in a docker container.
3. "Test" and "prod" envs with different databases and services.

Business:
1. Can an international invoice & national invoice with the same number exist in the system?
2. The same debtor has to be duplicated if they have international & national invoices (PL[NIP] and [NIP]).
3. Removing company with any invoices should not be possible.

Queue:
1. New invoice -> add to queue.

Generator:
1. New invoice message -> generator gets this and generates a PDF -> saves it somewhere?
2. Get the invoice PDF -> add to queue synchronously -> either get the PDF or generate it and get.

Company totals service:
1. On startup launch and calculate company totals.
2. New invoice message -> add to totals.