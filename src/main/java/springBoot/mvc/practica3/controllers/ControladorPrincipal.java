package springBoot.mvc.practica3.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import springBoot.mvc.practica3.model.Producto;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ControladorPrincipal {


    @Autowired
    private RestTemplate restTemplate;

    @Value("${rest.server.url}")
    private String restServerUrl;

    @GetMapping("/")
    public String select() {
        return "index";
    }

    @GetMapping("/newProduct")
    public String sendGreeting(Model model) {
        model.addAttribute("producto", new Producto());
        return "crearProducto";
    }

    @GetMapping("/show/{codigo}")
    public String mostrarProducto(Model model, @PathVariable String codigo) {
        Producto producto = restTemplate.getForObject(restServerUrl + "show/" + codigo, Producto.class);
        model.addAttribute("Producto", producto);
        return "mostrarProductoListaProductos";
    }

    @PostMapping("/newProduct")
    public String readGreeting(@ModelAttribute Producto producto) {
        restTemplate.exchange(restServerUrl + "newProduct", HttpMethod.POST, new HttpEntity<>(producto), Producto.class);
        return "mostrarProductoAnadidoCorrecto";
    }

    @RequestMapping(value = "/delete/{codigo}")
    public String remove(@PathVariable String codigo) {
        restTemplate.delete(restServerUrl + "delete/" + codigo);
        //restTemplate.exchange(restServerUrl + "delete/" + codigo, HttpMethod.POST, new HttpEntity<>());
        return "redirect:/list";
    }

    @GetMapping(value = "/list")
    public String getList(Model model) {
        List<Producto> listaProductos = restTemplate.getForObject(restServerUrl + "list", List.class);
        model.addAttribute("listaProductos", listaProductos);
        return "listaProductos";
    }


}