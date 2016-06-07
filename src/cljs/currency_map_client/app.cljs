(ns currency-map-client.app
  (:require [goog.net.XhrIo :as xhr]
            [goog.dom :as dom]
            [cljs.core.async :as a :refer [put! chan <! >! close!]]
            [cljs.reader :as reader]
            [clojure.string]
            [currency-map-client.fx :as fx])
  (:require-macros [cljs.core.async.macros :as async-macros :refer [go]]))
(enable-console-print!)

(extend-type js/NodeList
  ISeqable
  (-seq [array] (array-seq array 0)))

(def currency-country-map
  (let [a (atom [])]
    (go (xhr/send "currency-country-mapping.edn"
                  (fn [evt] (let [res (-> evt .-target .getResponseText reader/read-string)]
                              (reset! currency-country-map res)))))
    a))

(defn init []
  (let [c (.. js/document (createElement "DIV"))]
    (aset c "innerHTML" "<p>i'm dynamically created</p>")
    (.. js/document (getElementById "container") (appendChild c))))

;;(go (println (<! (get-fx-rate-for-date "2016-05-22"))))
;;(years-ago 0)
#_(http-get "http://apilayer.net/api/historical?access_key=2b54e936f1a7e0944f03cd840189b813&date=2014-05-22" #(js/console.log ))

(defn get-dom-node-for-country-code [country-code]
  (first (goog.dom.getElementsByClass country-code)))

(defn color-a-country! [country-code color]
  (set! (-> (get-dom-node-for-country-code (clojure.string/lower-case country-code)) .-style .-fill) color))
