# Change Log

## v0.8.6 
* **API retrocompatibility break** with `v0.8.4`: `JavaUtilPropertySource.from` don't throw
  exceptions if the property sources are available. Instead they return an `Optional`.

## v0.8.4
* Fixed `NullPointerException` error on `JavaUtilPropertySource`.
* Added `URL` to the list of supported configurable types.
* Deprecated file-based constructors for `JavaUtilPropertySource` (use
  `JavaUtilPropertySource.from(*)` static method instead)

## v0.8.2
* First public version.