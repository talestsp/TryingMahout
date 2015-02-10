import java.io.File;
import java.util.List;

import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.math.Arrays;

/**
 * Mahout in Action
 * Session 2.2 - A simple user-based recommender program with Mahout
 * @author tales tenorio pimentel
 *
 */

public class RecommenderIntro22 {
	
	final static int userId = 1;

	public static void main(String[] args) throws Exception {
		//all the preference, user, and item data needed in the	computation
		DataModel model = new FileDataModel(new File("csv/intro.csv"));
				
		UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
		
		UserNeighborhood neighborhood = new NearestNUserNeighborhood(2, similarity, model);
		
		System.out.println(Arrays.toString(neighborhood.getUserNeighborhood(userId)));
		
		Recommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

		List<RecommendedItem> recommendations = recommender.recommend(userId, 3);

		for (RecommendedItem recommendation : recommendations) {
			System.out.println(recommendation);
		}
	}
}
