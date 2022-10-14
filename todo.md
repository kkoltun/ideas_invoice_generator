# Readme

A PoC of the service used to record and generate invoices.

## Todo

1. Tests
2. Infrastructure (database) extracted out. Abstraction for this.
3. NBP service caching responses in DB.
4. Add PLN invoice.
5. "document" instead of PDF, format adjusted in the request header
6. Update the "created" and "lastupdated" fields correctly and automatically

Queue:
1. New invoice -> add to queue.

Generator:
1. New invoice message -> generator gets this and generates a PDF -> saves it somewhere?
2. Get the invoice PDF -> add to queue synchronously -> either get the PDF or generate it and get.

Actor totals service:
1. On startup launch and calculate actor totals.
2. New invoice message -> add to totals.