import com.example.VideoRentalNew.model.Movie;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/movies")
    public String viewMovies(Model model) {
        // Method accessible only to users with ADMIN role
        List<Movie> movies = movieService.getAllMovies();
        model.addAttribute("movies", movies);
        return "admin/movie-list";
    }

    @PostMapping("/movies")
    public String addMovie(@ModelAttribute Movie movie, Model model) {
        // Method accessible only to users with ADMIN role
        movieService.saveMovie(movie);
        return "redirect:/admin/movies";
    }

    @PostMapping("/movies/return/{id}")
    public String returnMovie(@PathVariable("id") Integer id, Model model) {
        // Method accessible only to users with ADMIN role
        movieService.markAsReturned(id);
        return "redirect:/admin/borrowed-movies";
    }

    @GetMapping("/borrowed-movies")
    public String viewBorrowedMovies(Model model) {
        // Method accessible only to users with ADMIN role
        List<BorrowedMovie> borrowedMovies = movieService.getBorrowedMovies();
        model.addAttribute("borrowedMovies", borrowedMovies);
        return "admin/borrowed-movies";
    }
}
