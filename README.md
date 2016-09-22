# cljs-text-field

A [re-frame](https://github.com/Day8/re-frame) application designed to be a sandbox for a custom text-field component.

## What needs to be done, in order

* [x] Basic, simple input field with same appearance and css of existing field on basic behaviour
* [x] Solve caret position problem
* [x] Format number but display raw value on modification
* [x] Handle visual feedback on focus and blur
* Handle inline error

## Development Mode

Figwheel port has been altered.

### Run application

```
lein clean
lein figwheel
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3456](http://localhost:3456).

## Production Build


To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```
