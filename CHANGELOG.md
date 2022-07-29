# Change Log

## v0.9.1
* Fixed a bug that prevented Kaconf to work with JVM versions higher than 11.
  * **IMPORTANT**: if your JVM version is 12 or higher, you won't be able to set properties into
    `final` values.

## v0.9.0
* Updated gradle wrapper
* Allow multiple names for a property, e.g.: `@Property({"user.name", "USER_NAME"})`

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