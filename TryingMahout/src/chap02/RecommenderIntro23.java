package chap02;
import java.io.File;
import java.io.IOException;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.apache.mahout.common.RandomUtils;
/**
 * Mahout in Action
 * Session 2.3 - Configuring and running an evaluation of a recommender
 * @author tales tenorio pimentel
 *
 */

public class RecommenderIntro23 {

	public static void main(String[] args) throws IOException {

		RandomUtils.useTestSeed();
		DataModel model = new FileDataModel(new File("csv/intro.csv"));
		RecommenderEvaluator evaluator = new AverageAbsoluteDifferenceRecommenderEvaluator();
		// RecommenderEvaluator evaluator = new RMSRecommenderEvaluator();

		// recommender builder para avaliar o recommender
		RecommenderBuilder builder = new RecommenderBuilder() {
			@Override
			public Recommender buildRecommender(DataModel model)
					throws TasteException {

				// a metrica de similaridade usada aqui foi Pearson
				// define o user mais perto
				UserSimilarity similarity = new PearsonCorrelationSimilarity(
						model);

				// computa a vizinhança toda
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(2,
						similarity, model);
				return new GenericUserBasedRecommender(model, neighborhood,
						similarity);
			}
		};

		// avaliação da qualiade do recomendador
		double score;
		try {
			score = evaluator.evaluate(builder, null, model, 0.896, 1.0);
			System.out.println(score);
		} catch (TasteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
