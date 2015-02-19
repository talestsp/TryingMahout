package chap03;
import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.DataModelBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

public class Chap03 {

	public static void main(String[] args) throws TasteException, IOException {

		execute31();
		execute32();
		execute36();

	}
	
	private static void execute36() throws TasteException, IOException{
		System.out.println("Executing example 3.6");
		//Use GenericBooleanPrefDataModel
				DataModel model = new GenericBooleanPrefDataModel(GenericBooleanPrefDataModel.toDataMap(new FileDataModel(new File("data/ua.base"))));
				RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
				RecommenderBuilder recommenderBuilder = new RecommenderBuilder() {
					public Recommender buildRecommender(DataModel model)
							throws TasteException {
						
						System.out.println(model.getNumUsers() + " users");
						System.out.println(model.getNumItems() + " items");
						
						UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
						UserNeighborhood neighborhood = new NearestNUserNeighborhood(10, similarity, model);
						return new GenericUserBasedRecommender(model, neighborhood,
								similarity);
					}
				};
				
				DataModelBuilder modelBuilder = new DataModelBuilder() {
					public DataModel buildDataModel(
							FastByIDMap<PreferenceArray> trainingData) {
						return new GenericBooleanPrefDataModel(

								//Build a	GenericBooleanPrefDataModel
								GenericBooleanPrefDataModel.toDataMap(trainingData));
					}
				};
				
				
				double score = evaluator.evaluate(recommenderBuilder, modelBuilder,
						model, 0.9, 1.0);
				System.out.println(score);
				System.out.println("End of example 3.6 \n");
	}

	private static void execute32() {
		System.out.println("Executing example 3.2");
		FastByIDMap<PreferenceArray> preferences = new FastByIDMap<PreferenceArray>();
		PreferenceArray prefsForUser1 = new GenericUserPreferenceArray(10);
		prefsForUser1.setUserID(0, 1L);
		prefsForUser1.setItemID(0, 101L);
		prefsForUser1.setValue(0, 3.0f);
		prefsForUser1.setItemID(1, 102L);
		prefsForUser1.setValue(1, 4.5f);

		preferences.put(1L, prefsForUser1);
		DataModel model = new GenericDataModel(preferences);
		System.out.println("End of example 3.2 \n");
	}

	private static void execute31() {
		System.out.println("Executing example 3.1");
		PreferenceArray user1Prefs = new GenericUserPreferenceArray(2);
		user1Prefs.setUserID(0, 1L);
		user1Prefs.setItemID(0, 101L);
		user1Prefs.setValue(0, 2.0f);
		user1Prefs.setItemID(1, 102L);
		user1Prefs.setValue(1, 3.0f);
		Preference pref = user1Prefs.get(1);
		System.out.println("End of example 3.1 \n");
	}

}
