Create your first index

```http request
PUT /my-first-index
```

Add some data to your newly created index

```http request
PUT /my-first-index/_doc/1
{"Description": "To be or no to be, that is the question."}
```

Retrieve the data to see that it was added properly

```http request
GET /my-first-index/_doc/1
```

After verifying that the data is correct, delete the document

```http request
DELETE /my-first-index/_doc/1
```

Finally, delete the index

```http request
DELETE /my-first-index
```