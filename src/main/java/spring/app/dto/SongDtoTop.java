package spring.app.dto;
/*
 *
 *@Data 10.05.2020
 *@autor Fedorov Yuri
 *@project security-boot-thymeleaf
 *
 */

import java.util.Set;

public class SongDtoTop  implements Comparable<SongDtoTop>{
    private Long id;
    private String name;
    private String authorName;

    private Long amount ;
    private int [][]point;

    public SongDtoTop(Long id, String name, String authorName) {
        this.id = id;
        this.name = name;
        this.authorName = authorName;
    }

    public SongDtoTop(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public int[][] getPoint() {
        return point;
    }

    public void setPoint(int[][] point) {
        this.point = point;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @Override
    public int compareTo(SongDtoTop o) {
        if(amount<o.amount) {
            return 1;
        }
        if(amount>o.amount) {
            return -1;
        }
        return 0;
    }
}
