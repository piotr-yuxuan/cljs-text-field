(defproject cljs-text-field "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.9.89"]
                 [reagent "0.6.0-rc"]
                 [re-frame "0.8.0"]
                 ;; Custom supplementary dependencies
                 [data-frisk-reagent "0.2.4" :exclusions [cljsjs/react-with-addons]]
                 [cljs-react-material-ui "0.2.21" :exclusions [cljsjs/material-ui]]
                 [cljsjs/material-ui "0.15.4-0"]]
  :plugins [[lein-maven-s3-wagon "0.2.5"]
            [lein-environ "1.0.2"]
            [lein-gen "0.2.2"]
            [lein-cljsbuild "1.1.4"]]
  :min-lein-version "2.5.3"
  :source-paths ["src/clj"]
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]
  :figwheel {:css-dirs    ["resources/public/css"]
             :server-port 3456}
  :profiles
  {:dev
   {:dependencies []
    :plugins      [[lein-figwheel "0.5.7"]]}}
  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "cljs-text-field.core/mount-root"}
     :compiler     {:main                 cljs-text-field.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true}}
    {:id           "min"
     :source-paths ["src/cljs"]
     :compiler     {:main            cljs-text-field.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}]})
