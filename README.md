# traQR

traQR is an "indoor navigation app" based on QR codes that can run from any web browser(desktop or mobile).

#Usage - Getting Directions

##Method 1 - With QR code

The user scans the traQR QR code at a given location. The user selects their desired destination from the resulting page. 
The user is then presented a results page which gives them directions on how to get from their current location to their desired destination.

##Method 2- From computer

The user goes to the URL `/directions`. They are presented with choices for origin and destination. After the use selects their origin 
and desired destination, they are presented a results page which gives them the requested directions.

#Administration - Loading directions

Administrators upload locations and print QR codes. They then make connections between these locations to enable navigation. 
Administrators only have to make connections between direct locations. For example, given the following map:
<pre>
a-->b-->c
</pre>
only two connections are neccessary: `a->b` and `b->c` in order for traQR to be able to give directions to get from location `a` to 
location `c`.

#Technical Details

Directions are generating using <a href="https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm">Dijkstra's algorithm</a>.

Technologies used:

- <a href="https://pdfbox.apache.org">Apache PDFBox (PDF generation)</a>
- <a href="http://freemarker.org">FreeMarker (HTML templating)</a>
- <a href="http://www.eclipse.org/jetty/">Jetty (web server)</a>
- <a href="https://www.sqlite.org">sqlite (database)</a>
- <a href="https://github.com/zxing/zxing">Zebra Cross (QR codes)<a>
