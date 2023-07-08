# All-Document-Reader

This library reads any type of documents like (doc, docx, xls, xlsx, ppt, pptx,pdf, rtf, csv, json, html, xml,txt,and kotlin)F files.

Just use this 4 line code

        Intent intent = new Intent(context, All_Document_Reader_Activity.class);
        intent.putExtra("path", path);
        intent.putExtra("fromAppActivity", true);
        context.startActivity(intent);

  val intent = Intent(this, All_Document_Reader_Activity::class.java)
        intent.putExtra("path", path)
        intent.putExtra("fromAppActivity", true)
        startActivity(intent)
	



# Gradle
Step 1

add jitpack repository 


allprojects {

		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
  
	}
 


step 2
add this dependency 
	       
	  implementation 'com.github.ahmadullahpk:all-documents-reader:1.0.1'

  # Maven
	<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

 

      <dependency>
	    <groupId>com.github.ahmadullahpk</groupId>
	    <artifactId>all-document-reader</artifactId>
	    <version>Tag</version>
	</dependency>

 # Contact me if you have problem
Fiverr: https://www.fiverr.com/s/rRb0Yr


 
 
