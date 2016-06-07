(ns currency-map-client.app-test
  (:require-macros [cljs.test :refer [deftest testing is]])
  (:require [cljs.test :as t]
            [currency-map-client.app :as app]))

(deftest test-arithmetic []
  (is (= (+ 0.1 0.2) 0.3) "Something foul is a float."))
