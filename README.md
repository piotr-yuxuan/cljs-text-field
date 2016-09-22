# cljs-text-field

A [re-frame](https://github.com/Day8/re-frame) application designed to be a sandbox for a custom text-field component.

## What needs to be done, in order

* Basic, simple input field with same appearance and css of existing field on basic behaviour
* Solve caret position problem
* Format number but display raw value on modification
* Handle visual feedback on focus and blur
* Handle inline error
* 

## Development Mode

### Run application:

```
lein clean
lein figwheel dev
```

Figwheel will automatically push cljs changes to the browser.

Wait a bit, then browse to [http://localhost:3449](http://localhost:3449).

## Production Build


To compile clojurescript to javascript:

```
lein clean
lein cljsbuild once min
```
