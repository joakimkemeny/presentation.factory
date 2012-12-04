<
div
data - bind = "foreach: collection, visibleSlide: !hidden()" >
…
<
div
class
= "scale" >
    < div
data - bind = "visible: !emptying(), style: {width: w}"
class
= "filling" >
    < span
data - bind = "text: current" > < /span>
    < /div>
…
<
/div>
< /div>
