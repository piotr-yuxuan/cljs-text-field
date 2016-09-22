(ns cljs-text-field.styles)

(def colours
  {:grey      "rgb(224, 224, 224)"
   :grey-text "rgba(0, 0, 0, 0.3)"
   :green     "rgb(114, 213, 114)"
   :red       "rgb(244, 67, 54)"})

(def styles
  {:hr-grey   {:border-width         "medium medium 1px"
               :border-style         "none none solid"
               :border-color         (:grey colours)
               :border-top-colors    :none
               :border-right-colors  :none
               :border-bottom-colors :none
               :border-left-colors   :none
               :border-image         :none
               :bottom               8
               :box-sizing           :content-box
               :margin               0
               :position             :absolute
               :width                "100%"}
   :hr-focus  {:border-width         "medium medium 2px"
               :border-style         "none none solid"
               :border-color         (:green colours)
               :border-top-colors    :none
               :border-right-colors  :none
               :border-bottom-colors :none
               :border-left-colors   :none
               :border-image         :none
               :bottom               8
               :box-sizing           :content-box
               :margin               0
               :transition           "all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms"
               :position             :absolute
               :width                "100%"}
   :input     {:padding          0
               :position         :relative
               :background-color :transparent
               :width            "100%"
               :border           "medium none"
               :outline          "medium none"
               :color            "rgba(0, 0, 0, 0.87)"
               :cursor           :initial
               :font             :inherit
               :height           "100%"
               :box-sizing       :border-box
               :margin-top       14}
   :label     {:position         "absolute"
               :top              38
               :transition       "all 450ms cubic-bezier(0.23, 1, 0.32, 1) 0ms"
               :z-index          1
               :cursor           :text
               :font-size        16
               :transform-origin "left top 0px"
               :pointer-events   :none
               :transform        "scale(0.75) translate(0px, -28px)"}
   :child     {:font-size        16
               :line-height      "24px"
               :width            "100%"
               :height           72
               :display          :inline-block
               :position         :relative
               :color            "rgba(0, 0, 0, 0.87)"
               :background-color :transparent
               :transition       "height 200ms cubic-bezier (0.23, 1, 0.32, 1)"}
   :container {:display       :inline-block
               :margin-top    -14
               :width         "100%"
               :font-family   "Roboto, sans-serif"
               :margin-bottom 20}})