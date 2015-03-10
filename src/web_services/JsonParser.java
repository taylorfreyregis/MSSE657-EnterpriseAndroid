package web_services;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import scis.ScisCourse;
import scis.ScisProgram;

import android.util.JsonReader;

public class JsonParser {

	public List<ScisProgram> parseProgramsJson(byte[] bytes) {
		InputStream stream = null;
		JsonReader reader = null;
		try {
            stream = new ByteArrayInputStream(bytes);
            reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
            return readProgramsMessageArray(reader);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (Exception ignore) {}
			try {
				reader.close();
			} catch (Exception ignore) {}
		}
		return null;
	}
	
	public List<ScisProgram> readProgramsMessageArray(JsonReader reader) throws IOException {
	     List<ScisProgram> messages = new ArrayList<ScisProgram>();
	     reader.beginArray();
	     
	     while (reader.hasNext()) {
	       messages.add(readProgramMessage(reader));
	     }
	     
	     reader.endArray();
	     return messages;
	}

    /*
        {
            "id": 1,
            "name": "CIS"
        }
    */

	public ScisProgram readProgramMessage(JsonReader reader) throws IOException {
		int id = -1;
		String title = null;
		
		reader.beginObject();
		while (reader.hasNext()) {
			String key = reader.nextName();
			if (key.equals("id")) {
				id = reader.nextInt();
			} else if (key.equals("name")) {
				title = reader.nextString();
			} else {
				reader.skipValue();
			}
		}
		reader.endObject();
		return new ScisProgram(id, title);
	}

    public List<ScisCourse> parseCoursesJson(byte[] bytes) {
        InputStream stream = null;
        JsonReader reader = null;
        try {
            stream = new ByteArrayInputStream(bytes);
            reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
            return readCoursesMessageArray(reader);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (Exception ignore) {}
            try {
                reader.close();
            } catch (Exception ignore) {}
        }
        return null;
    }

    public List<ScisCourse> readCoursesMessageArray(JsonReader reader) throws IOException {
        List<ScisCourse> messages = new ArrayList<ScisCourse>();
        reader.beginArray();

        while (reader.hasNext()) {
            messages.add(readCourseMessage(reader));
        }

        reader.endArray();
        return messages;
    }

    /*
    {
        "id": 6,
        "name": "MSCD 600 Database Architecture",
        "pid": {
            "id": 6,
            "name": "MSCD"
        }
    }
    */

    public ScisCourse readCourseMessage(JsonReader reader) throws IOException {
        int id = -1;
        String title = null;
        int programId = -1;

        reader.beginObject();
        while (reader.hasNext()) {
            String key = reader.nextName();
            if (key.equals("id")) {
                id = reader.nextInt();
            } else if (key.equals("name")) {
                title = reader.nextString();
            } else if (key.equals("pid")) {
                ScisProgram program = this.readProgramMessage(reader);
                if (program != null) {
                    programId = program.getId();
                }
            } else
            {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new ScisCourse(id, programId, title);
    }


    /*

	public List<ScisProgram> parseProgramsJson(String json) {
		InputStream stream = null;
		JsonReader reader = null;
		try {
            stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
            reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
            return readProgramsMessageArray(reader);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (Exception ignore) {}
			try {
				reader.close();
			} catch (Exception ignore) {}
		}
		return null;
	}

        public List<ScisCourse> parseCoursesJson(String json) {
        InputStream stream = null;
        JsonReader reader = null;
        try {
            stream = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
            reader = new JsonReader(new InputStreamReader(stream, "UTF-8"));
            return readCoursesMessageArray(reader);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                stream.close();
            } catch (Exception ignore) {}
            try {
                reader.close();
            } catch (Exception ignore) {}
        }
        return null;
    }
     */
}
