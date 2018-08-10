(defproject tempcalc "0.1.0-SNAPSHOT"
  :description "Calculate the temperature of a substance to add to other substance to reach a target temperature"
  :url "https://cjohansen.no/tempcalc/"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.9.946"]
                 [dumdom "1"]]
  :plugins [[lein-cljsbuild "1.1.7"]
            [test2junit "1.3.3"]
            [lein-doo "0.1.8"]]
  :profiles {:dev {:source-paths ["dev"]
                   :dependencies [[binaryage/devtools "0.9.9"]
                                  [com.cemerick/piggieback "0.2.2"]
                                  [expound "0.5.0"]
                                  [figwheel-sidecar "0.5.15" :exclusions [com.google.guava/guava commons-codec]]
                                  [org.clojure/test.check "0.9.0"]
                                  [org.clojure/tools.namespace "0.3.0-alpha4"]]
                   :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}
                   :main user}}
  :clean-targets ^{:protect false} ["resources/public/js" "target"]
  :figwheel {:css-dirs ["resources/public/styles"]}
  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src" "dev"]
                        :figwheel {:on-jsload "tempcalc.core/run"}
                        :compiler {:main tempcalc.dev
                                   :asset-path "/js/out"
                                   :output-to "resources/public/js/app.js"
                                   :output-dir "resources/public/js/out"
                                   :source-map-timestamp true
                                   :preloads [devtools.preload]
                                   :closure-output-charset "utf-8"}}
                       {:id "min"
                        :source-paths ["src"]
                        :compiler {:main tempcalc.run
                                   :output-to "resources/public/js/app.js"
                                   :output-dir "target/out"
                                   :optimizations :advanced
                                   :pretty-print false
                                   :closure-output-charset "utf-8"}}]}
  :jvm-opts ["-Djava.awt.headless=true"])
