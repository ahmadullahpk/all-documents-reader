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
# Step 1

add jitpack repository 


allprojects {

		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
  
	}

 # OR



 pluginManagement {



    repositories {
        google()
        mavenCentral()
        jcenter()
        gradlePluginPortal()
        maven { url 'https://jitpack.io' }

    }

    
}


dependencyResolutionManagement {


    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter()
        maven { url 'https://jitpack.io' }
    }

    
}


 


# Step 2
add this dependency 
	       
	 
    implementation 'com.github.ahmadullahpk:all-documents-reader:1.0.7'

# Step 3

Add build.gradle (App)


android {


........

 packagingOptions {
 
        exclude 'META-INF/DEPENDENCIES'
        exclude 'META-INF/INDEX.LIST'
	
    }
    
    ...........
 
}

# Step 4
add this line in gradle.properties 

android.enableJetifier=true
     


 # Contact me if you have problem
# Fiverr:
 https://www.fiverr.com/s/rRb0Yr
# WhatsApp:
+923115799224


 
 
